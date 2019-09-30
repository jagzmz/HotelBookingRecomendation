package com.turquoise.hotelbookrecomendation.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.turquoise.hotelbookrecomendation.Adapters.HotelAdapter;
import com.turquoise.hotelbookrecomendation.R;
import com.turquoise.hotelbookrecomendation.model.HotelResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class HomeFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HotelAdapter hotelAdapter;
    HotelResult hotelResult;
    RecyclerView recyclerView;
    int lastPos=0;

    public HomeFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFrag newInstance(String param1, String param2) {
        HomeFrag fragment = new HomeFrag();
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


        View view=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.hotelList);


        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onPause() {
        super.onPause();
        lastPos = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

    }

    public String getHotels(){
        SharedPreferences sp=getActivity().getSharedPreferences("hotel",Context.MODE_PRIVATE);
        Gson gson=new Gson();
        if(sp.contains("data")){

            return sp.getString("data",null);
        }
        else{
            return null;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        HotelAdapter hotelAdapter=new HotelAdapter(getContext());
        Gson gson=new Gson();

        if(getHotels()==null) {


            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(getContext().getAssets().open("hotels.json")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            hotelResult =gson.fromJson(br, HotelResult.class);


        }
        else {
            hotelResult=gson.fromJson(getHotels(),HotelResult.class);
        }

        hotelAdapter.setHotels(hotelResult.getHotels());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(hotelAdapter);

        recyclerView.getLayoutManager().scrollToPosition(lastPos);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void updateList() {
        if(hotelAdapter!=null){

            hotelAdapter.setHotels(hotelResult.getHotels());

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
