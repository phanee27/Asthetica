import React, { useState, useEffect } from 'react';
import axios from 'axios';
import config from './../../config';

const UploadArtwork = () => {
  const [artwork, setArtwork] = useState({
    title: '',
    artist: '',
    description: '',
    price: '',
    width: '',
    height: '',
    category: ''
  });
  const [artworkImage, setArtworkImage] = useState(null);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const [user, setUser] = useState(null);

  const imgbbApiKey = 'd2a1e2b1281d3140b05bb32312a95b35'; // Replace with your real key

  useEffect(() => {
    const loggedInUser = JSON.parse(localStorage.getItem('user'));
    if (loggedInUser) {
      setUser(loggedInUser);
      setArtwork(prev => ({ ...prev, artist: loggedInUser.username }));
    } else {
      setError("You must be logged in to upload artwork.");
    }
  }, []);

  const handleChange = (e) => {
    setArtwork({ ...artwork, [e.target.name]: e.target.value });
  };

  const handleImageChange = (e) => {
    setArtworkImage(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!user) {
      setError("You must be logged in to upload artwork.");
      return;
    }

    if (!artwork.title || !artwork.description || !artwork.price || !artwork.width || !artwork.height || !artwork.category || !artworkImage) {
      setError("Please fill out all fields and upload an image.");
      return;
    }

    try {
      // 1. Upload image to imgbb
      const formData = new FormData();
      formData.append('image', artworkImage);

      const imgbbResponse = await axios.post(`https://api.imgbb.com/1/upload?key=${imgbbApiKey}`, formData);
      const imageUrl = imgbbResponse.data.data.url;

      if (!imageUrl) {
        setError("Image upload failed.");
        return;
      }

      // 2. Send artwork details + imageUrl to backend
      const artworkData = {
        title: artwork.title,
        artist: artwork.artist,
        description: artwork.description,
        price: artwork.price,
        width: artwork.width,
        height: artwork.height,
        category: artwork.category,
        artistId: user.id,
        imageUrl: imageUrl
      };

      const backendResponse = await axios.post(`${config.url}/seller/upload`, artworkData);

      setMessage(backendResponse.data);
      setError("");

      // Clear form
      setArtwork({
        title: '',
        artist: user.username,
        description: '',
        price: '',
        width: '',
        height: '',
        category: ''
      });
      setArtworkImage(null);

    } catch (error) {
      console.error('Error uploading artwork:', error);
      setMessage("");
      setError(error.response?.data?.error?.message || error.message || "An error occurred while uploading artwork.");
    }
  };

  return (
    <div className="container mt-4">
      <h3 style={{ textAlign: "center", textDecoration: "underline" }}>Upload Artwork</h3>
      {
        message ? 
          <p style={{ textAlign: "center", color: "green", fontWeight: "bolder" }}>{message}</p> : 
          <p style={{ textAlign: "center", color: "red", fontWeight: "bolder" }}>{error}</p>
      }
      <form onSubmit={handleSubmit} encType="multipart/form-data">
        <div className="mb-3">
          <label>Title:</label>
          <input type="text" className="form-control" name="title" value={artwork.title} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label>Artist:</label>
          <input type="text" className="form-control" name="artist" value={artwork.artist} disabled />
        </div>
        <div className="mb-3">
          <label>Description:</label>
          <textarea className="form-control" name="description" rows="3" value={artwork.description} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label>Price (in INR):</label>
          <input type="number" step="0.01" className="form-control" name="price" value={artwork.price} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label>Width (in cm):</label>
          <input type="number" step="0.01" className="form-control" name="width" value={artwork.width} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label>Height (in cm):</label>
          <input type="number" step="0.01" className="form-control" name="height" value={artwork.height} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label>Category:</label>
          <input type="text" className="form-control" name="category" value={artwork.category} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label>Artwork Image:</label>
          <input type="file" className="form-control" onChange={handleImageChange} required />
        </div>
        <button type="submit" className="btn btn-primary">Upload Artwork</button>
      </form>
    </div>
  );
};

export default UploadArtwork;
