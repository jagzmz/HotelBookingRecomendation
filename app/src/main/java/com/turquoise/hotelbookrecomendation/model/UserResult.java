
package com.turquoise.hotelbookrecomendation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResult {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("bookings")
    @Expose
    private List<Booking> bookings = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}
