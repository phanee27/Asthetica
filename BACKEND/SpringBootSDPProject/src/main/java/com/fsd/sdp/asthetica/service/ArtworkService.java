package com.fsd.sdp.asthetica.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fsd.sdp.asthetica.model.Artwork;

public interface ArtworkService 
{
	public String addartwork(Artwork artwork);
	public List<Artwork> viewallartworks();
	public Artwork viewartworkbyid(int id);
	public List<Artwork> viewartworksbyartist(int artistId);	
	public String uploadImageToCloudinary(MultipartFile file);

}
