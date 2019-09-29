package com.turquoise.hotelbookrecomendation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.turquoise.hotelbookrecomendation.Utils.Notif;
import com.turquoise.hotelbookrecomendation.model.Hotel;
import com.turquoise.hotelbookrecomendation.model.HotelResult;
import com.turquoise.hotelbookrecomendation.model.UserHotel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Recommendation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Recommendation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Recommendation extends Fragment implements RecommendationAdapter.CartListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static Set<String> tagSet=new HashSet<>();
    RecommendationAdapter recommendationAdapter;


    private OnFragmentInteractionListener mListener;

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
        View v= inflater.inflate(R.layout.fragment_recommendation, container, false);

        RecyclerView recyclerView=v.findViewById(R.id.hotelList);

       this.recommendationAdapter =new RecommendationAdapter(getContext(), this);


        recommendationAdapter.setHotels(getHotelWithTags());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recommendationAdapter);



        return v;



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        String sa="";

        for(String sss:Recommendation.tagSet){
            sa+=sss;
        }
        Log.d("ASASSA", "onCreateView: "+sa);
        Notif.showToast(getActivity(),sa);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public Set<Hotel> getHotelWithTags(){

        Gson gson=new Gson();

        BufferedReader br= null;
        try {
            br = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("hotels.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }



        HotelResult hotelResult;
        hotelResult =gson.fromJson(br, HotelResult.class);

        Set<String> registeredHotels=new HashSet<>();

        Set<Hotel> hotelList=new HashSet<>();

        for(UserHotel userHotel:MainActivity.bookings.getUserHotels()){
            Log.d("CHECK", "getHotelWithTags: "+userHotel.getTags()+" ");
                registeredHotels.add(userHotel.getName());


        }

        for(Hotel hotel:hotelResult.getHotels()){
            for(String hh: hotel.getTags().split("\n")){
                if(Recommendation.tagSet.contains(hh)&&!registeredHotels.contains(hotel.getName())){

                    hotelList.add(hotel);

                }
            }
        }

        return hotelList;

    }

    public void updateList() {
        if(recommendationAdapter!=null){

            recommendationAdapter.setHotels(getHotelWithTags());

        }
    }

    @Override
    public void click(String hotel_name) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
