package com.koley.musrights.controllers;

import com.koley.musrights.datasets.UserAvatarColorsDataset;
import com.koley.musrights.domains.*;
import com.koley.musrights.misc.Role;
import com.koley.musrights.repositories.*;
import com.koley.musrights.services.AdminService;
import com.koley.musrights.services.AuthenticationService;
import com.koley.musrights.services.PageService;
import com.koley.musrights.services.SearchAndFiltersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    AdminService adminService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAvatarsRepository userAvatarsRepository;
    @Autowired
    CompositionRepository compositionRepository;
    @Autowired
    PageService<Composition> compositionPageService;
    @Autowired
    SearchAndFiltersService searchAndFiltersService;
    @Autowired
    BuyRepository buyRepository;
    @Autowired
    RentRepository rentRepository;
    @Autowired
    RentHistoryRepository rentHistoryRepository;
    @Autowired
    BuyHistoryRepository buyHistoryRepository;
    private final int compositionsPerPage = 12;
    User user = new User();
    boolean isSignedIn = false;

    @GetMapping("/")
    public String getHomepage() {
        return "redirect:/home/1";
    }

    @GetMapping("/home/{page}")
    public String getHomeFiltered(@PathVariable(value = "page") int page,
                                  @RequestParam(value = "search", required = false) String search,
                                  @RequestParam(value = "sort", required = false) String sort,
                                  @RequestParam(value = "filters", required = false) Genres[] filters,
                                  Model model) {
        if (isSignedIn) {
            model.addAttribute("user", user);
            user = userRepository.getByName(user.getName());//update admin id if refill DB
        }
        boolean isAdmin = userRepository.existsByName("admin");
        if (!isAdmin) {
            adminService.createAdmin();
        }


        if (!searchAndFiltersService.isSet() || page == 1 && searchAndFiltersService.isChanged(search, sort, filters, user)) {
            searchAndFiltersService.insertParams(user, search, sort, filters);
            searchAndFiltersService.build();
        }


        List<Composition> compositions = searchAndFiltersService.getData();

        compositionPageService.construct(compositions, compositionsPerPage);
        Page<Composition> currentPage = compositionPageService.getPage(page);
        model.addAttribute("isSignedIn", isSignedIn);

        if (!currentPage.isSingle()) {
            model.addAttribute("labels", currentPage.getLabels());
        }
        if (!currentPage.isNull()) {
            model.addAttribute("compositions", currentPage.getElements());
        }
        model.addAttribute("genres", new ArrayList<>(EnumSet.allOf(Genres.class)));
        model.addAttribute("search", search);
        model.addAttribute("sort", searchAndFiltersService.getSort());
        model.addAttribute("filters", searchAndFiltersService.getFilters());
        model.addAttribute("userAvatar", userAvatarsRepository.getByUserId(user.getId()));

        return "index";
    }

    @GetMapping("/stopRent/{id}")
    public String stopRent(@PathVariable(value = "id") long id,
                           Model model) {

        UserRent crent = rentRepository.getByUserIdAndCompositionId(user.getId(), id);
        rentRepository.deleteById(crent.getId());
        List<Composition> compositions = compositionRepository.findAllByOwnerId(user.getId());
        List<UserRent> rents = rentRepository.findAllByUserId(user.getId());
        List<Composition> ownCompositions = new ArrayList<>();
        List<Composition> rentCompositions = new ArrayList<>();
        List<Composition> boughtCompositions = new ArrayList<>();
        for (Composition composition : compositions) {
            if (composition.isFirstOwner()) {
                ownCompositions.add(composition);
            } else boughtCompositions.add(composition);
        }
        for (UserRent rent : rents) {
            Composition composition = compositionRepository.getById(rent.getCompositionId());
            rentCompositions.add(composition);
        }
        model.addAttribute("ownMusic", ownCompositions);
        model.addAttribute("uploadedMusicCount", ownCompositions.size());
        model.addAttribute("boughtMusicCount", boughtCompositions.size());
        model.addAttribute("rentMusicCount", rentCompositions.size());
        model.addAttribute("user", user);
        model.addAttribute("isSignedIn", isSignedIn);
        model.addAttribute("username", user.getName());
        model.addAttribute("rents", rentCompositions);
        model.addAttribute("buying", boughtCompositions);
        model.addAttribute("userAvatar", userAvatarsRepository.getByUserId(user.getId()));
        if (user.getName().equals("admin")) {
            return "admin";
        }
        return "userpage";
    }

    @GetMapping("/editComposition/{id}")
    public String getEditPage(@PathVariable(value = "id") long id,
                              Model model) {
        Composition composition = compositionRepository.getById(id);
        List<Genres> genres = new ArrayList<>(EnumSet.allOf(Genres.class));
        Genres currentGenre = null;

        if (composition.getGenre() != null) {
            int index = 0;
            for (Genres genre : genres) {

                if (composition.getGenre().equals(genre)) {
                    currentGenre = genres.get(index);
                    genres.remove(index);
                    break;
                }
                index++;
            }
        }
        model.addAttribute("composition", composition);
        model.addAttribute("genres", genres);
        if (currentGenre != null) {
            model.addAttribute("currentGenre", currentGenre);
        }
        return "editComposition";
    }

    @PostMapping("/processEdit/{id}")
    public String processEdit(@PathVariable(value = "id") long id,
                              @RequestParam(value = "songName") String Nname,
                              @RequestParam(value = "songAuthor") String Nauthor,
                              @RequestParam(value = "songGenre", required = false) Genres Ngenre,
                              @RequestParam(value = "sell") boolean sell,
                              Model model) {

        Composition composition = compositionRepository.getById(id);
        composition.setAvailableToBuy(sell);
        composition.setName(Nname);
        composition.setAuthor(Nauthor);
        if(Ngenre != null){
            composition.setGenre(Ngenre);
        }
        compositionRepository.save(composition);

        composition = compositionRepository.getById(id);
        List<Genres> genres = new ArrayList<>(EnumSet.allOf(Genres.class));
        Genres currentGenre = null;
        if (composition.getGenre() != null) {
            int index = 0;
            for (Genres genre : genres) {

                if (composition.getGenre().equals(genre)) {
                    currentGenre = genres.get(index);
                    genres.remove(index);
                    break;
                }
                index++;
            }
        }
        model.addAttribute("composition", composition);
        model.addAttribute("genres", genres);
        if (currentGenre != null) {
            model.addAttribute("currentGenre", currentGenre);
        }
        model.addAttribute("passed", true);
        return "editComposition";
    }

    @GetMapping("/userpage/{username}")
    public String getUserpage(Model model, @PathVariable(value = "username") String username) {
        if (isSignedIn && username.equals(user.getName())) {
            if (username.equals("admin")) {
                user = userRepository.getByName("admin");
            }
            List<Composition> compositions = compositionRepository.findAllByOwnerId(user.getId());
            List<UserRent> rents = rentRepository.findAllByUserId(user.getId());
            List<Composition> ownCompositions = new ArrayList<>();
            List<Composition> rentCompositions = new ArrayList<>();
            List<Composition> boughtCompositions = new ArrayList<>();
            for (Composition composition : compositions) {
                if (composition.isFirstOwner()) {
                    ownCompositions.add(composition);
                } else boughtCompositions.add(composition);
            }
            for (UserRent rent : rents) {
                Composition composition = compositionRepository.getById(rent.getCompositionId());
                rentCompositions.add(composition);
            }

            model.addAttribute("ownMusic", ownCompositions);
            model.addAttribute("uploadedMusicCount", ownCompositions.size());
            model.addAttribute("boughtMusicCount", boughtCompositions.size());
            model.addAttribute("rentMusicCount", rentCompositions.size());
            model.addAttribute("user", user);
            model.addAttribute("isSignedIn", isSignedIn);
            model.addAttribute("username", username);
            model.addAttribute("rents", rentCompositions);
            model.addAttribute("buying", boughtCompositions);
            model.addAttribute("userAvatar", userAvatarsRepository.getByUserId(user.getId()));
        } else {
            return "redirect:/signin";
        }
        if (username.equals("admin")) {
            return "admin";
        }
        return "userpage";
    }

    @GetMapping("rent/{songID}")
    public String getRentPage(@PathVariable(value = "songID") long songID,
                              Model model) {
        Composition composition = compositionRepository.getById(songID);
        model.addAttribute("composition", composition);
        model.addAttribute("owner", userRepository.getById(composition.getOwnerId()));
        model.addAttribute("currentUser", user);
        return "rent";
    }

    @GetMapping("buy/{songID}")
    public String getBuyPage(@PathVariable(value = "songID") long songID,
                             Model model) {
        Composition composition = compositionRepository.getById(songID);
        model.addAttribute("composition", composition);
        model.addAttribute("owner", userRepository.getById(composition.getOwnerId()));
        model.addAttribute("currentUser", user);
        return "buy";
    }

    @PostMapping("processRent/{songID}")
    public String processRent(@PathVariable(value = "songID") long songID,
                              @RequestParam(value = "listenCount") String lcount,
                              Model model) {

        lcount = lcount.replace(",", "");
        int count = Integer.parseInt(lcount.replace("прослушиваний", "").trim());

        Composition composition = compositionRepository.getById(songID);
        UserRent rent;
        if (rentRepository.existsByCompositionIdAndUserId(songID, user.getId())) {
            rent = rentRepository.getByUserIdAndCompositionId(user.getId(), songID);
            rent.setListenCount(rent.getListenCount() + count);
        } else {
            rent = new UserRent();
            rent.setListenCount(count);
        }
        rent.setRentDate(new Date());
        rent.setUserId(user.getId());
        rent.setCompositionId(composition.getId());

        RentHistoryLine rentHistoryLine = new RentHistoryLine();
        rentHistoryLine.setLineDate(rent.getRentDate());
        rentHistoryLine.setUserId(rent.getUserId());
        rentHistoryLine.setCompositionId(rent.getCompositionId());
        rentHistoryLine.setListenCount(rent.getListenCount());
        composition.setRentTimes(composition.getRentTimes() + 1);
        user.setActions(user.getActions() + 1);
        userRepository.save(user);
        compositionRepository.save(composition);
        rentRepository.save(rent);
        rentHistoryRepository.save(rentHistoryLine);

        model.addAttribute("composition", composition);
        model.addAttribute("listenCount", count);
        model.addAttribute("currentUser", user);
        return "successfulRent";
    }

    @PostMapping("processBuy/{songID}")
    public String processBuy(@PathVariable(value = "songID") long songID,
                             Model model) {
        Composition composition = compositionRepository.getById(songID);
        UserBuying buying;
        if (buyRepository.existsByCompositionId(composition.getId())) {
            buying = buyRepository.getByCompositionId(composition.getId());
            buying.setBuyingDate(new Date());
            buying.setUserId(user.getId());
        } else {
            buying = new UserBuying();
            buying.setBuyingDate(new Date());
            buying.setUserId(user.getId());
            buying.setCompositionId(composition.getId());
        }
        if (rentRepository.existsByCompositionIdAndUserId(songID, user.getId())) {
            UserRent rent = rentRepository.getByUserIdAndCompositionId(user.getId(), songID);
            rentRepository.deleteById(rent.getId());
        }
        composition.setOwnerId(user.getId());
        composition.setFirstOwner(false);
        composition.setAvailableToBuy(false);
        composition.setBuyTimes(composition.getBuyTimes() + 1);
        compositionRepository.save(composition);
        buyRepository.save(buying);

        BuyingHistoryLine buyingHistoryLine = new BuyingHistoryLine();
        buyingHistoryLine.setUserId(buying.getUserId());
        buyingHistoryLine.setCompositionId(buying.getCompositionId());
        buyingHistoryLine.setLineDate(buying.getBuyingDate());
        buyHistoryRepository.save(buyingHistoryLine);
        user.setActions(user.getActions() + 1);
        userRepository.save(user);
        //stop  all rent due to  new buy
        //rentRepository.deleteAllByCompositionId(composition.getId());

        model.addAttribute("composition", composition);
        model.addAttribute("currentUser", user);
        return "successfulBuy";
    }

    @GetMapping("/signin")
    public String getLoginPage() {
        return "sign-in";
    }

    @GetMapping("/signup")
    public String getRegistrationPage() {
        return "sign-up";
    }

    @PostMapping("/login")
    public String signIn(@RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password,
                         Model model) {

        System.out.println("Got username: " + username);
        System.out.println("Got password: " + password);
        List<ErrorMessage> errors = authenticationService.validateLogin(username, password);
        if (errors.isEmpty()) {
            user = userRepository.findByName(username);
            isSignedIn = true;
            return "redirect:/";
        } else {
            for (ErrorMessage error : errors) {
                model.addAttribute(error.getType().getTemplateValue(), error);
                error.print();
            }
            isSignedIn = false;
            model.addAttribute("username", username);
        }
        return "sign-in";
    }

    @GetMapping("/register")
    public String registerNewUser(@RequestParam(value = "username") String username,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "email") String email,
                                  @RequestParam(value = "password") String password,
                                  @RequestParam(value = "repeatPassword") String repeatPassword,
                                  Model model) {

        System.out.println("Got username: " + username);
        System.out.println("Got name: " + name);
        System.out.println("Got email: " + email);
        System.out.println("Got password: " + password);
        System.out.println("Got repeatPassword: " + repeatPassword);
        List<ErrorMessage> errors = authenticationService.validateRegistration(username, name, password, repeatPassword, email);
        if (errors.isEmpty()) {
            user.setName(username);
            user.setFullName(authenticationService.normalize(name));
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(Role.USER);
            UserAvatarColorsDataset colorsDataset = new UserAvatarColorsDataset();
            userRepository.save(user);
            user = userRepository.getByName(username);
            int size = colorsDataset.size;
            int index = (int) Math.floor(Math.random() * (size + 1));
            UserAvatar avatar = adminService.createUserAvatar(user, colorsDataset.userColors.get(index), colorsDataset.userSecondaryColors.get(index));
            userAvatarsRepository.save(avatar);
            isSignedIn = true;
            return "redirect:/";
        } else {
            isSignedIn = false;
            for (ErrorMessage error : errors) {
                model.addAttribute(error.getType().getTemplateValue(), error);
                error.print();
            }
            model.addAttribute("invalid_authentication", "есть ошибки в регистрации");
            model.addAttribute("username", username);
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            model.addAttribute("password", password);
            model.addAttribute("repeatPassword", repeatPassword);
        }
        return "sign-up";
    }

    @GetMapping("/signout")
    public String signOut() {
        isSignedIn = false;
        user = new User();
        return "redirect:/signin";
    }
}
