package com.fsd.sdp.asthetica.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fsd.sdp.asthetica.dto.ArtworkDTO;
import com.fsd.sdp.asthetica.model.Artwork;
import com.fsd.sdp.asthetica.service.ArtworkService;
import com.fsd.sdp.asthetica.enumeration.Status;

@RestController
@RequestMapping("/seller")
@CrossOrigin("*")
public class SellerController {

    @Autowired
    private ArtworkService artworkService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadArtwork(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam double width,
            @RequestParam double height,
            @RequestParam String category,
            @RequestParam double price,
            @RequestParam int artistId,
            @RequestParam("artworkImage") MultipartFile file) {

        try {
            // Call your Cloudinary service here to upload the file and get the URL
            String imageUrl = artworkService.uploadImageToCloudinary(file);

            Artwork artwork = new Artwork();
            artwork.setTitle(title);
            artwork.setDescription(description);
            artwork.setWidth(width);
            artwork.setHeight(height);
            artwork.setCategory(category);
            artwork.setPrice(price);
            artwork.setStatus(Status.PENDING); // Default status as PENDING
            artwork.setImageUrl(imageUrl);
            artwork.setArtistId(artistId);

            String output = artworkService.addartwork(artwork);
            return ResponseEntity.ok(output);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/myartworks")
    public ResponseEntity<List<ArtworkDTO>> viewMyArtworks(@RequestParam int artistId) {
        List<Artwork> artworkList = artworkService.viewartworksbyartist(artistId);
        List<ArtworkDTO> dtoList = new ArrayList<>();

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
            dto.setArtistId(art.getArtistId());
            dtoList.add(dto);
        }

        return ResponseEntity.ok(dtoList);
    }
}

//	    @GetMapping("/displayartworkimage")
//	    public ResponseEntity<byte[]> displayArtworkImage(@RequestParam int id) throws Exception {
//	        // Fetch the artwork from the database by ID
//	        Artwork artwork = artworkService.viewartworkbyid(id);
//
//	        // Get the image Blob
//	        Blob imageBlob = artwork.getImage();
//
//	        // Convert Blob to byte[]
//	        byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
//
//	        // Return the image as a byte array, with the correct content type (JPEG or other types)
//	        return ResponseEntity.ok()
//	                             .contentType(MediaType.IMAGE_JPEG)  // Assuming JPEG for now, adjust if needed
//	                             .body(imageBytes);
//	    }

	
}
