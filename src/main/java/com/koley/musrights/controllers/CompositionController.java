package com.koley.musrights.controllers;

import com.koley.musrights.services.CompositionService;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/composition")
public class CompositionController {
    @Autowired
    UserController userController;

    @Autowired
    CompositionService compositionService;

    @PostMapping(value = "add", produces = "audio/mpeg")
    public String addComposition(@RequestParam("file") MultipartFile file) throws IOException, InvalidDataException, UnsupportedTagException, NotSupportedException {
        compositionService.save(file, userController.user);

        return "Saved";
    }

    @DeleteMapping("remove")
    public String removeComposition() {
        return "";
    }
}
