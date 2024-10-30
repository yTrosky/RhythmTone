package com.example.thiago_project.controller;

import com.example.thiago_project.models.MusicasDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import com.example.thiago_project.models.Musicas;
import com.example.thiago_project.repository.MusicasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.*;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/musicas")
public class MusicasController {
    @Autowired
    private MusicasRepository repo;

    @GetMapping({"", "/"})
    public String showMusicasList(Model model) {
        List<Musicas> musicas = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("musicas", musicas);
        return "musicas/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        MusicasDto musicasDto = new MusicasDto();
        model.addAttribute("musicasDto", musicasDto);
        return "musicas/CreateMusicas";
    }

    @PostMapping("/create")
    public String createMusicas(
            @Valid @ModelAttribute MusicasDto musicasDto,
            BindingResult result
    ) {

        if (musicasDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("musicasDto", "imageFile", "Image file is required"));
        }
        if (result.hasErrors()) {
            return "musicas/CreateMusicas";
        }

        MultipartFile image = musicasDto.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        Musicas musicas = new Musicas();
        musicas.setName(musicasDto.getName());
        musicas.setArtist(musicasDto.getArtist());
        musicas.setCategory(musicasDto.getCategory());
        musicas.setDescription(musicasDto.getDescription());
        musicas.setCreatedAt(createdAt);
        musicas.setImageFileName(storageFileName);

        repo.save(musicas);

        return "redirect:/musicas";
    }

    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ) {

        try {
            Musicas musicas = repo.findById(id).get();
            model.addAttribute("musicas", musicas);

            MusicasDto musicasDto = new MusicasDto();
            musicasDto.setName(musicas.getName());
            musicasDto.setArtist(musicas.getArtist());
            musicasDto.setCategory(musicas.getCategory());
            musicasDto.setDescription(musicas.getDescription());

            model.addAttribute("musicasDto", musicasDto);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/musicas";
        }
        return "musicas/EditMusicas";
    }

    @PostMapping("/edit")
    public String updateProduct(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute MusicasDto musicasDto,
            BindingResult result
    ) {
        try {
            Musicas musicas = repo.findById(id).get();
            model.addAttribute("musicas", musicas);

            if (result.hasErrors()) {
                return "musicas/EditMusicas";
            }

            if (!musicasDto.getImageFile().isEmpty()) {
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + musicas.getImageFileName());

                try {
                    Files.delete (oldImagePath);
                }
                catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }

                MultipartFile image = musicasDto.getImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() +"_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption. REPLACE_EXISTING);
                }
                musicas.setImageFileName(storageFileName);
            }

            musicas.setName (musicasDto.getName());
            musicas.setArtist (musicasDto.getArtist());
            musicas.setCategory (musicasDto.getCategory());
            musicas.setDescription (musicasDto.getDescription());
            repo.save(musicas);
        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/musicas";
    }

    @GetMapping("/delete")
    public String deleteMusica(
            @RequestParam int id
    ) {
        try {
            Musicas musicas = repo.findById(id).get();

            Path imagePath = Paths.get("public/images/" + musicas.getImageFileName());
            try {
                Files.delete(imagePath);
            }
            catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }

            repo.delete(musicas);

        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/musicas";
    }
}
