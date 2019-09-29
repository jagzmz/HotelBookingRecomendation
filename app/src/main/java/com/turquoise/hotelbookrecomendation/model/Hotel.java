package com.turquoise.hotelbookrecomendation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Hotel implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ratings")
    @Expose
    private String ratings;
    @SerializedName("visits")
    @Expose
    private String visits;
    @SerializedName("completedBookings")
    @Expose
    private String completedBookings;
    @SerializedName("draftBookings")
    @Expose
    private String draftBookings;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getVisits() {
        return visits;
    }

    public void setVisits(String visits) {
        this.visits = visits;
    }

    public String getCompletedBookings() {
        return completedBookings;
    }

    public void setCompletedBookings(String completedBookings) {
        this.completedBookings = completedBookings;
    }

    public String getDraftBookings() {
        return draftBookings;
    }

    public void setDraftBookings(String draftBookings) {
        this.draftBookings = draftBookings;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}