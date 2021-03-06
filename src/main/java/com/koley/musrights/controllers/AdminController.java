package com.koley.musrights.controllers;

import com.koley.musrights.domains.*;
import com.koley.musrights.repositories.CompositionRepository;
import com.koley.musrights.repositories.RentRepository;
import com.koley.musrights.repositories.UserAvatarsRepository;
import com.koley.musrights.repositories.UserRepository;
import com.koley.musrights.services.AdminService;
import com.koley.musrights.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    AdminService adminService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CompositionRepository compositionRepository;
    @Autowired
    UserAvatarsRepository userAvatarsRepository;
    @Autowired
    RentRepository rentRepository;
    //!WARNING! unauthorized admin actions
    User user;
    UserAvatar userAvatar;

    @PostMapping("/admin/wipedata")
    public String wipeData(@RequestParam(value = "adminPassword") String adminPassword, Model model) {
        user = userRepository.getByName("admin");
        userAvatar = userAvatarsRepository.getByUserId(user.getId());
        List<ErrorMessage> errorList = authenticationService.validateLogin(user.getName(), adminPassword);
        if (!errorList.isEmpty()) {
            for (ErrorMessage errorMessage : errorList) {
                model.addAttribute(errorMessage.getType().getTemplateValue(), errorMessage.getMessage());
            }
        }
        adminService.wipeData();
        user = userRepository.getByName("admin");
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

        int uploadedListSize = compositions.size();
        model.addAttribute("ownMusic", ownCompositions);
        model.addAttribute("uploadedMusicCount", ownCompositions.size());
        model.addAttribute("boughtMusicCount", boughtCompositions.size());
        model.addAttribute("rentMusicCount", rentCompositions.size());
        model.addAttribute("user", user);
        model.addAttribute("isSignedIn", true);
        model.addAttribute("username", "admin");
        model.addAttribute("rents", rentCompositions);
        model.addAttribute("buying", boughtCompositions);
        model.addAttribute("userAvatar", userAvatarsRepository.getByUserId(user.getId()));

        return "admin";
    }

    @PostMapping("/admin/addComposition")
    public String addFakeComposition(@RequestParam(value = "compositionName") String compositionName,
                                     @RequestParam(value = "compositionAuthor") String compositionAuthor,
                                     Model model) {
        user = userRepository.getByName("admin");
        userAvatar = userAvatarsRepository.getByUserId(user.getId());
        List<ErrorMessage> errorMessages = adminService.ValidateComposition(compositionName, compositionAuthor);

        if (errorMessages.isEmpty()) {
            Composition composition = new Composition(compositionName, compositionAuthor, user);

            compositionRepository.save(composition);
        } else {
            for (ErrorMessage error : errorMessages) {
                model.addAttribute(error.getType().getTemplateValue(), error);
                error.print();
            }
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

        int uploadedListSize = compositions.size();
        model.addAttribute("ownMusic", ownCompositions);
        model.addAttribute("uploadedMusicCount", ownCompositions.size());
        model.addAttribute("boughtMusicCount", boughtCompositions.size());
        model.addAttribute("rentMusicCount", rentCompositions.size());
        model.addAttribute("user", user);
        model.addAttribute("isSignedIn", true);
        model.addAttribute("username", "admin");
        model.addAttribute("rents", rentCompositions);
        model.addAttribute("buying", boughtCompositions);
        model.addAttribute("userAvatar", userAvatarsRepository.getByUserId(user.getId()));
        return "admin";
    }

    @PostMapping("/admin/fillDB")
    public String fillDatabase(Model model) {
        user = userRepository.getByName("admin");
        userAvatar = userAvatarsRepository.getByUserId(user.getId());
        List<ErrorMessage> errorMessages = adminService.createFakeDatabase();

        if (!errorMessages.isEmpty()) {
            for (ErrorMessage error : errorMessages) {
                model.addAttribute(error.getType().getTemplateValue(), error);
                error.print();
            }
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

        int uploadedListSize = compositions.size();
        model.addAttribute("ownMusic", ownCompositions);
        model.addAttribute("uploadedMusicCount", ownCompositions.size());
        model.addAttribute("boughtMusicCount", boughtCompositions.size());
        model.addAttribute("rentMusicCount", rentCompositions.size());
        model.addAttribute("user", user);
        model.addAttribute("isSignedIn", true);
        model.addAttribute("username", "admin");
        model.addAttribute("rents", rentCompositions);
        model.addAttribute("buying", boughtCompositions);
        model.addAttribute("userAvatar", userAvatarsRepository.getByUserId(user.getId()));
        return "admin";
    }
}
