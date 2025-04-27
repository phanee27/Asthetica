package com.fsd.sdp.asthetica.model;

import com.fsd.sdp.asthetica.enumeration.Status;
import jakarta.persistence.*;

@Entity
@Table(name = "artwork_table")
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artwork_id")
    private int id;

    @Column(name = "artwork_title", nullable = false, length = 100)
    private String title;

    @Column(name = "artwork_description", nullable = false, length = 500)
    private String description;

    @Column(name = "artwork_width", nullable = false)
    private double width;

    @Column(name = "artwork_height", nullable = false)
    private double height;

    @Column(name = "artwork_category", nullable = false, length = 50)
    private String category;

    @Column(name = "artwork_price", nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "artwork_status", nullable = false, length = 20)
    private Status status;

    @Column(name = "artwork_image_url", nullable = false, length = 500)
    private String imageUrl;  // Store Cloudinary image URL

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artist_id", nullable = false)
    private User artist;

    // Getters and Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public User getArtist() { return artist; }
    public void setArtist(User artist) { this.artist = artist; }
}
