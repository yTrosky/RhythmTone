package com.example.thiago_project.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class MusicasDto {

    @NotEmpty(message = "The name is required")
    private String name;

    @NotEmpty (message = "The artist is required")
    private String artist;

    @NotEmpty (message = "The name is required")
    private String category;

    @Size(min = 10, message = "The description should be at least 10 characters")
    @Size (max = 2000, message = "The description cannot exceed 2000 characters")
    private String description;
    private MultipartFile imageFile;

    public @NotEmpty(message = "The name is required") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "The name is required") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "The artist is required") String getArtist() {
        return artist;
    }

    public void setArtist(@NotEmpty(message = "The artist is required") String artist) {
        this.artist = artist;
    }

    public @NotEmpty(message = "The name is required") String getCategory() {
        return category;
    }

    public void setCategory(@NotEmpty(message = "The name is required") String category) {
        this.category = category;
    }

    public @Size(min = 10, message = "The description should be at least 10 characters") @Size(max = 2000, message = "The description cannot exceed 2000 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 10, message = "The description should be at least 10 characters") @Size(max = 2000, message = "The description cannot exceed 2000 characters") String description) {
        this.description = description;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
