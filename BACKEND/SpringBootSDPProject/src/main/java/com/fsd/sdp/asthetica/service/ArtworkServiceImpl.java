package com.fsd.sdp.asthetica.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fsd.sdp.asthetica.model.Artwork;
import com.fsd.sdp.asthetica.repository.ArtworkRepository;

@Service
public class ArtworkServiceImpl implements ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;

    private final Cloudinary cloudinary;

    @Autowired
    public ArtworkServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String addartwork(Artwork artwork) {
        artworkRepository.save(artwork);
        return "Artwork added Successfully";
    }

    @Override
    public List<Artwork> viewallartworks() {
        return artworkRepository.findAll();
    }

    @Override
    public Artwork viewartworkbyid(int id) {
        return artworkRepository.findById(id).orElse(null);
    }

    @Override
    public List<Artwork> viewartworksbyartist(int artistId) {
        return artworkRepository.findByArtistId(artistId);
    }

    @Override
    public String uploadImageToCloudinary(MultipartFile file) {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url"); // Return the uploaded image URL
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }
}
