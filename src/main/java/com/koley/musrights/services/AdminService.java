package com.koley.musrights.services;

import com.koley.musrights.datasets.MusicDataset;
import com.koley.musrights.datasets.UserAvatarColorsDataset;
import com.koley.musrights.datasets.UserNamesDataset;
import com.koley.musrights.domains.Composition;
import com.koley.musrights.domains.ErrorMessage;
import com.koley.musrights.domains.User;
import com.koley.musrights.domains.UserAvatar;
import com.koley.musrights.misc.ErrorType;
import com.koley.musrights.misc.Role;
import com.koley.musrights.repositories.CompositionRepository;
import com.koley.musrights.repositories.UserAvatarsRepository;
import com.koley.musrights.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CompositionRepository compositionRepository;
    @Autowired
    UserAvatarsRepository userAvatarsRepository;

    public void wipeData() {
        userRepository.deleteAll();
        compositionRepository.deleteAll();
        userAvatarsRepository.deleteAll();
        createAdmin();
    }

    public List<ErrorMessage> ValidateComposition(String name, String author) {
        List<ErrorMessage> errorMessages = new ArrayList<>();
        if (compositionRepository.existsByNameAndAuthor(name, author)) {
            ErrorMessage errorMessage = new ErrorMessage("Ошибка! такая композиция уже существует", ErrorType.ALREADY_EXISTS);
            errorMessages.add(errorMessage);
        }
        return errorMessages;
    }

    public List<ErrorMessage> createFakeDatabase() {
        List<ErrorMessage> errorMessages = new ArrayList<>();
        boolean hasErrors = saveUsersWithAvatars();
        if (hasErrors) {
            ErrorMessage errorMessage = new ErrorMessage("Ошибка! несоответсвие размеров датасетов", ErrorType.INCOMPATIBLE_DATASETS_SIZES);
            errorMessages.add(errorMessage);
            return errorMessages;
        }

        hasErrors = saveMusic();
        if (hasErrors) {
            ErrorMessage errorMessage = new ErrorMessage("Ошибка в нумерации песен", ErrorType.INCOMPATIBLE_MUSIC_NUMERATION);
            errorMessages.add(errorMessage);
            return errorMessages;
        }

        return errorMessages;
    }

    private boolean saveUsersWithAvatars() {
        int size = 0;
        UserNamesDataset userNamesDataset = new UserNamesDataset();
        UserAvatarColorsDataset userAvatarColorsDataset = new UserAvatarColorsDataset();

        int userListSize = userNamesDataset.size;
        int userAvatarsSize = userAvatarColorsDataset.size;

        if (userListSize != userAvatarsSize) {
            return true;
        }

        List<String> usernames = new ArrayList<>(userNamesDataset.usernames.keySet());

        for (int i = 0; i < userListSize; i++) {
            String key = usernames.get(i);
            User user = createUser(key, userNamesDataset.usernames.get(key));

            if (!userRepository.existsByName(user.getName())) {
                userRepository.save(user);
                User savedUser = userRepository.getByName(key);
                UserAvatar userAvatar = createUserAvatar(savedUser,
                        userAvatarColorsDataset.userColors.get(i),
                        userAvatarColorsDataset.userSecondaryColors.get(i));
                userAvatarsRepository.save(userAvatar);
            }
            size++;
        }
        System.out.println("Users created: " + size);
        return false;
    }

    private boolean saveMusic() {
        UserNamesDataset userNamesDataset = new UserNamesDataset();
        List<String> usernames = new ArrayList<>(userNamesDataset.usernames.keySet());
        MusicDataset musicDataset = new MusicDataset();

        int userListSize = userNamesDataset.size;
        int musicListSize = musicDataset.size;

        int integerMusicToUsersRatio = musicListSize / userListSize;
        int remainderMusicToUsersRatio = musicListSize % userListSize;

        int compositionNumber = 0;
        for (String username : usernames) {
            User user = userRepository.getByName(username);
            compositionNumber = getCompositionNumber(musicDataset, integerMusicToUsersRatio, compositionNumber, user);
        }
        if (remainderMusicToUsersRatio != 0) {
            User user = userRepository.getByName(usernames.get(0));
            compositionNumber = getCompositionNumber(musicDataset, remainderMusicToUsersRatio, compositionNumber, user);
        }
        return compositionNumber != musicListSize;
    }

    private int getCompositionNumber(MusicDataset musicDataset, int integerMusicToUsersRatio, int compositionNumber, User user) {
        for (int i = 0; i < integerMusicToUsersRatio; i++) {
            Composition composition = musicDataset.compositions.get(compositionNumber);
            composition.setFake(true);
            composition.setOwnerId(user.getId());
            if (!compositionRepository.existsByNameAndAuthor(composition.getName(), composition.getAuthor())) {
                compositionRepository.save(composition);
            }
            compositionNumber++;
        }
        return compositionNumber;
    }


    private User createUser(String name, String fullName) {
        User user = new User();
        user.setName(name);
        user.setFullName(fullName);
        user.setEmail(name + "@mail.com");
        user.setPassword("password");
        user.setRole(Role.USER);
        return user;
    }

    public UserAvatar createUserAvatar(User user, String primaryColor, String secondaryColor) {
        UserAvatar userAvatar = new UserAvatar();
        userAvatar.setUserId(user.getId());
        userAvatar.setAppliedUserColor(primaryColor);
        userAvatar.setAppliedSecondaryUserColor(secondaryColor);
        if (user.getRole().equals(Role.ADMIN)) {
            userAvatar.setInitials("admin");
        } else {
            userAvatar.generateInitials(user.getFullName());
        }

        return userAvatar;
    }

    public void createAdmin() {
        UserAvatarColorsDataset colors = new UserAvatarColorsDataset();
        User admin = new User();
        admin.setName("admin");
        admin.setFullName("Administrator User");
        admin.setEmail("admin@horeca.localhost");
        admin.setPassword("admin");
        admin.setRole(Role.ADMIN);

        if (!userRepository.existsByName(admin.getName())) {
            userRepository.save(admin);
            admin = userRepository.getByName("admin");
            UserAvatar adminAvatar = createUserAvatar(admin, colors.userColors.get(-1), colors.userSecondaryColors.get(-1));
            userAvatarsRepository.save(adminAvatar);
        }
    }
}
