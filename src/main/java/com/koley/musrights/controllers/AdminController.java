package com.koley.musrights.controllers;

import com.koley.musrights.domains.Composition;
import com.koley.musrights.domains.ErrorMessage;
import com.koley.musrights.domains.User;
import com.koley.musrights.repositories.CompositionRepoitory;
import com.koley.musrights.repositories.UserRepository;
import com.koley.musrights.services.AdminService;
import com.koley.musrights.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
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
    CompositionRepoitory compositionRepoitory;

    User user;

    @PostMapping("/admin/wipedata")
    public String wipeData(@RequestParam (value = "adminPassword")String adminPassword, Model model){
        user = userRepository.getByName("admin");
        List<ErrorMessage> errorList = authenticationService.validateLogin(user.getName(), adminPassword);
        if(!errorList.isEmpty()){
            for (ErrorMessage errorMessage: errorList) {
                model.addAttribute(errorMessage.getType().getTemplateValue(), errorMessage.getMessage());
            }
        }
        adminService.wipeData();
        model.addAttribute("user", user);
        model.addAttribute("isSignedIn", true);
        return "admin";
    }

    @PostMapping("/admin/addComposition")
    public String addFakeComposition(@RequestParam(value = "compositionName")String compositionName,
                                     @RequestParam(value = "compositionAuthor")String compositionAuthor,
                                     Model model){
        user = userRepository.getByName("admin");
        List<ErrorMessage> errorMessages = adminService.ValidateComposition(compositionName, compositionAuthor);

        if(errorMessages.isEmpty()){
            Composition composition = new Composition(compositionName, compositionAuthor, user);

            compositionRepoitory.save(composition);
        }
        else{
            for (ErrorMessage error : errorMessages) {
                model.addAttribute(error.getType().getTemplateValue(), error);
                error.print();
            }

        }
        model.addAttribute("user", user);
        model.addAttribute("isSignedIn", true);
        return "admin";
    }

    @PostMapping("/admin/fillDB")
    public String fillDatabase(Model model){
        user = userRepository.getByName("admin");
        List<ErrorMessage> errorMessages = adminService.createFakeDatabase();

        if (!errorMessages.isEmpty()) {
            for (ErrorMessage error : errorMessages) {
                model.addAttribute(error.getType().getTemplateValue(), error);
                error.print();
            }

        }
        model.addAttribute("user", user);
        model.addAttribute("isSignedIn", true);
        return "admin";
    }
}
