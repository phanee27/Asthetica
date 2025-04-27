package com.fsd.sdp.asthetica.service;

import com.fsd.sdp.asthetica.model.Artwork;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArtworkService {
    String addArtwork(Artwork artwork);
    List<Artwork> viewAllArtworks();
    Artwork viewArtworkById(int id);
    List<Artwork> viewArtworksByArtist(int artistId);
    String uploadImageToImgBB(MultipartFile file);  // Method to upload image to ImgBB
}
