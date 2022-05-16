package com.koley.musrights.controllers;

import com.koley.musrights.services.CompositionService;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/composition")
public class CompositionController {
    @Autowired
    UserController userController;

    @Autowired
    CompositionService compositionService;

    @GetMapping(value = "play")
    public InputStreamResource playComposition(
            @RequestParam("id") long idOfComposition
    ) throws FileNotFoundException {
        return compositionService.getCompositionResource(idOfComposition);
    }

    @PostMapping(value = "add", produces = "audio/mpeg")
    public String addComposition(
            @RequestParam("file") MultipartFile file
    ) throws IOException, InvalidDataException, UnsupportedTagException, NotSupportedException {
        compositionService.save(file, userController.user);

        return "Saved";
    }

    @DeleteMapping(value = "remove")
    public String removeComposition(@RequestParam("id") long idOfComposition) throws IOException {
        compositionService.remove(idOfComposition, userController.user);

        return "Deleted";
    }
}
