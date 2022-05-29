package com.koley.musrights.controllers;

import com.koley.musrights.datasets.UserAvatarColorsDataset;
import com.koley.musrights.domains.*;
import com.koley.musrights.misc.Role;
import com.koley.musrights.repositories.CompositionRepository;
import com.koley.musrights.repositories.UserAvatarsRepository;
import com.koley.musrights.repositories.UserRepository;
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
    private final int compositionsPerPage = 12;
    User user = new User();
    boolean isSignedIn = false;

    @GetMapping("/")
    public String getHomepage(){
        return "redirect:/home/1";
    }

    @GetMapping("/home/{page}")
    public String getHomeFiltered(@PathVariable(value = "page")int page,
                                  @RequestParam(value = "search", required = false)String search,
                                  @RequestParam(value = "sort", required = false)String sort,
                                  @RequestParam(value = "filters", required = false) Genres[] filters,
                                  Model model) {
        if (isSignedIn) {
            model.addAttribute("user", user);
            user = userRepository.getByName(user.getName());//update admin id if refill DB
        }
        boolean isAdmin = userRepository.existsByName("admin");
        if(!isAdmin){
            adminService.createAdmin();
        }


        if(!searchAndFiltersService.isSet() || page == 1 && searchAndFiltersService.isChanged(search, sort, filters)){
            searchAndFiltersService.insertParams(search, sort, filters);
            searchAndFiltersService.build();
        }


        List<Composition> compositions = searchAndFiltersService.getData();

        compositionPageService.construct(compositions, compositionsPerPage);
        Page<Composition> currentPage = compositionPageService.getPage(page);
        model.addAttribute("isSignedIn", isSignedIn);

        if(!currentPage.isSingle()) {
            model.addAttribute("labels", currentPage.getLabels());
        }
        if(!currentPage.isNull()) {
            model.addAttribute("compositions", currentPage.getElements());
        }
        model.addAttribute("genres",  new ArrayList<>(EnumSet.allOf(Genres.class)));
        model.addAttribute("search", search);
        model.addAttribute("sort", searchAndFiltersService.getSort());
        model.addAttribute("filters", searchAndFiltersService.getFilters());
        model.addAttribute("userAvatar", userAvatarsRepository.getByUserId(user.getId()));

        return "index";
    }

    @GetMapping("/userpage/{username}")
    public String getUserpage(Model model, @PathVariable (value = "username") String username){
        if (isSignedIn && username.equals(user.getName())) {
            if(username.equals("admin")){
                user = userRepository.getByName("admin");
            }
            List<Composition> compositions = compositionRepository.findAllByOwnerId(user.getId());
            int uploadedListSize = compositions.size();
            model.addAttribute("ownMusic", compositions);
            model.addAttribute("uploadedMusicCount", uploadedListSize);
            model.addAttribute("user", user);
            model.addAttribute("isSignedIn", isSignedIn);
            model.addAttribute("username", username);
            model.addAttribute("userAvatar", userAvatarsRepository.getByUserId(user.getId()));
        }
        else{
            return "redirect:/signin";
        }
        if(username.equals("admin")){
            return "admin";
        }
        return "userpage";
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
            int size = colorsDataset.size;
            int index = (int)Math.floor(Math.random()*(size+1));
            UserAvatar avatar = adminService.createUserAvatar(user, colorsDataset.userColors.get(index), colorsDataset.userSecondaryColors.get(index));
            userRepository.save(user);
            userAvatarsRepository.save(avatar);
            isSignedIn = true;
            return "redirect:/";
        } else {
            isSignedIn = false;
            for (ErrorMessage error : errors) {
                model.addAttribute(error.getType().getTemplateValue(), error);
                error.print();
            }
            model.addAttribute("invalid_authentication",  "есть ошибки в регистрации");
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
