package com.koley.musrights.services;

import com.koley.musrights.domains.Composition;
import com.koley.musrights.domains.User;
import com.koley.musrights.repositories.CompositionRepository;
import com.mpatric.mp3agic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class CompositionService {
    @Autowired
    CompositionRepository repository;

    //TODO 1) upload by any user (ownerId - user.id)-> resolve file location on disk

    public void save(MultipartFile file, User user) throws IOException, InvalidDataException, UnsupportedTagException, NotSupportedException {
        Mp3File mp3File = getMp3File(file);
        ID3v2 dataOfTrackByID3v2 = mp3File.getId3v2Tag();
        ID3v1 dataOfTrackByID3v1 = mp3File.getId3v1Tag();

        String filename = UUID.randomUUID().toString();
        String directories = "/music/" + user.getName() + "/";
        String fileExtension = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];

        Path pathToFile = Path.of(directories + '/' + filename + '.' + fileExtension);

        File compositionDir = new File(directories);

        Composition composition = null;

        if (dataOfTrackByID3v2 != null) {
            composition = new Composition(
                    dataOfTrackByID3v2.getTitle(),
                    dataOfTrackByID3v2.getArtist(),
                    user
            );
        }

        if (dataOfTrackByID3v1 != null) {
            composition = new Composition(
                    dataOfTrackByID3v1.getTitle(),
                    dataOfTrackByID3v1.getArtist(),
                    user
            );
        }

        assert composition != null;

        composition.setFake(false);
        composition.setDurationInSeconds((int) mp3File.getLengthInSeconds());
        composition.setFormatedDuration(getFormatedDuration(composition.getDurationInSeconds()));
        composition.setFileName(filename);

        if (!compositionDir.exists()) {
            compositionDir.mkdirs();
        }

        file.transferTo(pathToFile);

        repository.save(composition);
    }

    private static Mp3File getMp3File(MultipartFile file) throws IOException, InvalidDataException, UnsupportedTagException {
        Path filePath = Paths.get("tmp/", file.getOriginalFilename());

        file.transferTo(filePath);

        Mp3File mp3File = new Mp3File(filePath);

        Files.deleteIfExists(filePath);

        return mp3File;
    }

    //TODO 3) resolve music duration by service (composition duration = 90sec) 1:30 for frontend
    private static String getFormatedDuration(int duration) {
        int seconds = duration % 60;
        int minutes = duration / 60;

        String returnSeconds = seconds < 10 ? "0" + seconds : String.valueOf(seconds);
        String returnMinutes = minutes < 10 ? "0" + minutes : String.valueOf(minutes);

        return returnMinutes + ':' + returnSeconds;
    }

    //TODO 2) delete byId (and)-> delete file from disk
    public void remove(long idOfComposition, User user) throws IOException {
        Composition composition = repository.getById(idOfComposition);

        String directories = "/music/" + user.getName() + "/";

        Path pathToFile = Paths.get(directories + composition.getFileName() + ".mp3");

        Files.deleteIfExists(pathToFile);
        repository.delete(composition);
    }

    //TODO 4) resolve music playing (input[range]) position
    //TODO 5) listen by "play" click (isFake = false) (!)Fake means if audio src file is present(!)
}
