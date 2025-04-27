package com.fsd.sdp.asthetica.service;

import com.fsd.sdp.asthetica.model.Artwork;
import com.fsd.sdp.asthetica.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ArtworkServiceImpl implements ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;

    private static final String IMG_BB_API_URL = "https://api.imgbb.com/1/upload";
    private static final String IMG_BB_API_KEY = "d2a1e2b1281d3140b05bb32312a95b35";  // Replace with your ImgBB API key

    @Override
    public String addArtwork(Artwork artwork) {
        artworkRepository.save(artwork);
        return "Artwork added successfully";
    }

    @Override
    public List<Artwork> viewAllArtworks() {
        return artworkRepository.findAll();
    }

    @Override
    public Artwork viewArtworkById(int id) {
        return artworkRepository.findById(id).orElse(null);
    }

    @Override
    public List<Artwork> viewArtworksByArtist(int artistId) {
        return artworkRepository.findByArtistId(artistId);
    }

    @Override
    public String uploadImageToImgBB(MultipartFile file) {
        try {
            // Convert the image file to a byte array
            byte[] imageBytes = file.getBytes();

            // Prepare the request to ImgBB API
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new ByteArrayResource(imageBytes) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });

            // Set headers including the API key
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Client-API-Key " + IMG_BB_API_KEY);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Create a RestTemplate object and send a POST request to ImgBB API
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.exchange(IMG_BB_API_URL, HttpMethod.POST, requestEntity, Map.class);

            // Parse the response
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("data")) {
                Map<String, String> data = (Map<String, String>) responseBody.get("data");
                return data.get("url");  // Return the URL of the uploaded image
            } else {
                return "Error: Unable to upload image";
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
