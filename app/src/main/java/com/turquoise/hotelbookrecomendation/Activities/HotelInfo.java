package com.turquoise.hotelbookrecomendation.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.turquoise.hotelbookrecomendation.Adapters.RecommendationAdapter;
import com.turquoise.hotelbookrecomendation.Fragments.Recommendation;
import com.turquoise.hotelbookrecomendation.R;
import com.turquoise.hotelbookrecomendation.model.Booking;
import com.turquoise.hotelbookrecomendation.model.Hotel;
import com.turquoise.hotelbookrecomendation.model.HotelResult;
import com.turquoise.hotelbookrecomendation.model.UserHotel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HotelInfo extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView hotelImage;
    private TextView hotelDesc, views, drafts, completed;
    private Button book, draftBook;
    private RecommendationAdapter.HotelViewHolder hotelViewHolder;
    Hotel hotel;
    int pos;
    HotelResult hotelResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_info);

        toolbar = findViewById(R.id.toolbarInfo);
        hotelImage = findViewById(R.id.hotelImage);
        hotelDesc = findViewById(R.id.hotelDesc);
        book = findViewById(R.id.confirmBooking);
        draftBook = findViewById(R.id.draftBooking);
        views = findViewById(R.id.views);
        drafts = findViewById(R.id.draftText);
        completed = findViewById(R.id.completedText);


    }

    @Override
    protected void onResume() {
        super.onResume();

        hotel = (Hotel) getIntent().getExtras().getSerializable("data");


        hotelResult = new Gson().fromJson(getHotels(), HotelResult.class);
        pos = getIntent().getExtras().getInt("pos");

        toolbar.setTitle(hotel.getName());

        setSupportActionBar(toolbar);
        Picasso
                .with(HotelInfo.this)
                .load(Uri.parse(hotel.getImageUrl()))
                .into(hotelImage);
        hotelDesc.setText(hotel.getDescription());

        views.setText(hotelResult.getHotels().get(pos).getVisits() + " views");
        drafts.setText(hotelResult.getHotels().get(pos).getDraftBookings() + " drafts");
        completed.setText(hotelResult.getHotels().get(pos).getCompletedBookings() + " booked");


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setBooking(true);
                finish();

            }
        });
        draftBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setBooking(false);
                MainActivity.updatec(1);
                Log.d("ononon", "onClick: ");
                finish();

            }
        });
    }

    public String getHotels() {
        SharedPreferences sp = getSharedPreferences("hotel", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        if (sp.contains("data")) {
            return sp.getString("data", null);
        } else {
            return null;
        }

    }

    private void setBooking(Boolean complete) {


        UserHotel hotel1 = new UserHotel();
        hotel1.setName(hotel.getName());
        hotel1.setCompleted(complete);
        hotel1.setTags(hotel.getTags());

        Booking booking = new Booking();

        List<UserHotel> userHotels = MainActivity.bookings.getUserHotels();
        userHotels.add(hotel1);

        MainActivity.bookings.setUserHotels(userHotels);

        Set<String> s = new HashSet<>();
        for (UserHotel userHotel : MainActivity.bookings.getUserHotels()) {
            if (userHotel.getCompleted()) {
                for (String ss : userHotel.getTags().split("\n")) {
                    ss = ss.replace("null", "");
                    s.add(ss);
                }

            }
        }
        String sa = "";

        for (String sss : s) {
            sa += sss;
            Recommendation.tagSet.add(sss);
        }

        if (complete) {
            int c = Integer.valueOf(hotelResult.getHotels().get(pos).getCompletedBookings());
            hotelResult.getHotels().get(pos).setCompletedBookings(String.valueOf(c + 1));
            storeUpdates(hotelResult);
        } else {
            int c = Integer.valueOf(hotelResult.getHotels().get(pos).getDraftBookings());
            hotelResult.getHotels().get(pos).setDraftBookings(String.valueOf(c + 1));
            storeUpdates(hotelResult);
        }

    }

    public void storeUpdates(HotelResult hotelResult) {
        SharedPreferences.Editor spe = getSharedPreferences("hotel", Context.MODE_PRIVATE).edit();
        String save = new Gson().toJson(hotelResult);
        spe.putString("data", save);
        spe.apply();


    }
}
