package com.turquoise.hotelbookrecomendation.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.turquoise.hotelbookrecomendation.Activities.MainActivity;
import com.turquoise.hotelbookrecomendation.Adapters.RecommendationAdapter;
import com.turquoise.hotelbookrecomendation.R;
import com.turquoise.hotelbookrecomendation.model.Hotel;
import com.turquoise.hotelbookrecomendation.model.HotelResult;
import com.turquoise.hotelbookrecomendation.model.UserHotel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Recommendation extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static Set<String> tagSet = new HashSet<>();
    RecommendationAdapter recommendationAdapter;
    TextView placeholderEmpty;


    private HotelResult hotelResult;

    public Recommendation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Recommendation.
     */
    // TODO: Rename and change types and number of parameters
    public static Recommendation newInstance(String param1, String param2) {
        Recommendation fragment = new Recommendation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recommendation, container, false);

        placeholderEmpty=v.findViewById(R.id.emptyRecommendationPlaceHolder);
        RecyclerView recyclerView = v.findViewById(R.id.hotelList);

        this.recommendationAdapter = new RecommendationAdapter(getContext());


        recommendationAdapter.setHotels(getHotelWithTags());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recommendationAdapter);



        return v;


    }

    @Override
    public void onResume() {
        super.onResume();

        if(recommendationAdapter.getItemCount()==0){
            placeholderEmpty.setVisibility(View.VISIBLE);
        }
        else{
            placeholderEmpty.setVisibility(View.INVISIBLE);
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("activityresult", "onActivityResult: " + resultCode);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        String sa = "";

        for (String sss : Recommendation.tagSet) {
            sa += sss;
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public Set<Hotel> getHotelWithTags() {

        Gson gson = new Gson();

        if (getHotels() == null) {


            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("hotels.json")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            hotelResult = gson.fromJson(br, HotelResult.class);


        } else {
            hotelResult = gson.fromJson(getHotels(), HotelResult.class);
        }

        Set<String> registeredHotels = new HashSet<>();

        Set<Hotel> hotelList = new HashSet<>();

        for (UserHotel userHotel : MainActivity.bookings.getUserHotels()) {
            registeredHotels.add(userHotel.getName());


        }

        for (Hotel hotel : hotelResult.getHotels()) {
            for (String hh : hotel.getTags().split("\n")) {
                if (Recommendation.tagSet.contains(hh) && !registeredHotels.contains(hotel.getName())) {

                    hotelList.add(hotel);

                }
            }
        }

        return hotelList;

    }

    public void updateList() {
        if (recommendationAdapter != null) {

            recommendationAdapter.setHotels(getHotelWithTags());

        }
    }


    public String getHotels() {
        SharedPreferences sp = getActivity().getSharedPreferences("hotel", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        if (sp.contains("data")) {
            return sp.getString("data", null);
        } else {
            return null;
        }

    }


}
