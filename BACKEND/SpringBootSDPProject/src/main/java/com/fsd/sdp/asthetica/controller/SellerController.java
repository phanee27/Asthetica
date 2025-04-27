package com.fsd.sdp.asthetica.controller;

import com.fsd.sdp.asthetica.dto.ArtworkDTO;
import com.fsd.sdp.asthetica.enumeration.Status;
import com.fsd.sdp.asthetica.model.Artwork;
import com.fsd.sdp.asthetica.model.User;
import com.fsd.sdp.asthetica.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/seller")
@CrossOrigin("*")
public class SellerController {

    @Autowired
    private ArtworkService artworkService;

    // Endpoint to upload artwork
    @PostMapping("/upload")
    public ResponseEntity<String> uploadArtwork(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam double width,
            @RequestParam double height,
            @RequestParam String category,
            @RequestParam int artistId,
            @RequestParam("artworkImage") MultipartFile file) {
        try {
            // Check if the image file is provided
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("No image file provided.");
            }

            // Upload image to ImgBB (or another service)
            String imageUrl = artworkService.uploadImageToImgBB(file);
            if (imageUrl == null || imageUrl.isEmpty()) {
                return ResponseEntity.status(500).body("Error uploading image to image service.");
            }

            // Create a new Artwork object
            Artwork artwork = new Artwork();
            artwork.setTitle(title);
            artwork.setDescription(description);
            artwork.setPrice(price);
            artwork.setWidth(width);
            artwork.setHeight(height);
            artwork.setCategory(category);
            artwork.setImageUrl(imageUrl);
            artwork.setStatus(Status.AVAILABLE); // Or Status.PENDING depending on your logic

            // Create a User object and set only the ID (representing the artist)
            User artist = new User();
            artist.setId(artistId);

            // Set the artist
            artwork.setArtist(artist);

            // Save artwork to the database
            String result = artworkService.addArtwork(artwork);
            return ResponseEntity.ok("Artwork uploaded successfully: " + result);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading artwork: " + e.getMessage());
        }
    }

    // Endpoint to fetch all artworks by a specific artist (seller)
    @GetMapping("/myartworks")
    public ResponseEntity<List<ArtworkDTO>> viewMyArtworks(@RequestParam int artistId) {
        try {
            // Fetch all artworks from the database for the specific artist
            List<Artwork> artworkList = artworkService.viewArtworksByArtist(artistId);
            List<ArtworkDTO> dtoList = new ArrayList<>();

            // Convert each Artwork to an ArtworkDTO
            for (Artwork art : artworkList) {
                ArtworkDTO dto = new ArtworkDTO();
                dto.setId(art.getId());
                dto.setTitle(art.getTitle());
                dto.setDescription(art.getDescription());
                dto.setWidth(art.getWidth());
                dto.setHeight(art.getHeight());
                dto.setCategory(art.getCategory());
                dto.setPrice(art.getPrice());
                dto.setStatus(art.getStatus());
                dto.setImageUrl(art.getImageUrl());
                dto.setArtistId(art.getArtist().getId());
                dtoList.add(dto);
            }

            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Returning null as the body with a 500 error
        }
    }

}
