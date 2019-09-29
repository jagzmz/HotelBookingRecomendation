package com.turquoise.hotelbookrecomendation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelResult {

    @SerializedName("hotels")
    @Expose
    private List<Hotel> hotels = null;

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

}