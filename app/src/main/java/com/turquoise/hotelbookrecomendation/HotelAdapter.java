package com.turquoise.hotelbookrecomendation;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.turquoise.hotelbookrecomendation.Utils.Notif;
import com.turquoise.hotelbookrecomendation.model.Booking;
import com.turquoise.hotelbookrecomendation.model.Hotel;
import com.turquoise.hotelbookrecomendation.model.UserHotel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private View view;
    private HotelViewHolder hotelViewHolder;
    private List<Hotel> hotels;
    private final CartListener cartListener;

    public HotelAdapter(Context context, CartListener cartListener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.cartListener = cartListener;
    }

    public void setHotels(List<Hotel> lists) {
        this.hotels = lists;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.hotelcard, parent, false);
        hotelViewHolder = new HotelViewHolder(view);

        return hotelViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Picasso
                .with(context)
                .load(Uri.parse(hotels.get(position).getImageUrl()))
                .into(holder.hotelImage);

        holder.hotelRatings.setText(hotels.get(position).getRatings());
        holder.tags.setText(hotels.get(position).getTags());
        holder.hotelName.setText(hotels.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    class HotelViewHolder extends RecyclerView.ViewHolder {

        ImageView hotelImage;
        TextView hotelRatings, hotelName;
        TextView tags;
        Button bookButton;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelImage = itemView.findViewById(R.id.hotelImage);
            hotelRatings = itemView.findViewById(R.id.ratings);
            bookButton = itemView.findViewById(R.id.hotelBookButton);
            tags = itemView.findViewById(R.id.tagsList);
            hotelName = itemView.findViewById(R.id.hotelName);

            bookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    UserHotel hotel = new UserHotel();
                    hotel.setName(hotelName.getText().toString());
                    hotel.setCompleted(true);
                    hotel.setTags(tags.getText().toString());
                    Booking booking = new Booking();

                    List<UserHotel> userHotels = MainActivity.bookings.getUserHotels();
                    userHotels.add(hotel);
                    MainActivity.bookings.setUserHotels(userHotels);

                    cartListener.click("Hotel Name");
                    Set<String> s=new HashSet<>();
                    for(UserHotel userHotel:MainActivity.bookings.getUserHotels()){
                        if(userHotel.getCompleted()){
                            for(String ss:userHotel.getTags().split("\n")){
                                Log.d("reco", "onClick: "+ss);
                                ss=ss.replace("null","");

                                s.add(ss);
                            }

                        }
                    }
                    String sa="";

                    for(String sss:s){
                        sa+=sss;
                        Recommendation.tagSet.add(sss);
                    }
                    Notif.showToast(context,sa);
                }
            });



        }
    }

    interface CartListener {
        void click(String hotel_name);
    }

}
