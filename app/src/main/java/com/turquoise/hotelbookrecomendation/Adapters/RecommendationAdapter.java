package com.turquoise.hotelbookrecomendation.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.turquoise.hotelbookrecomendation.Activities.HotelInfo;
import com.turquoise.hotelbookrecomendation.R;
import com.turquoise.hotelbookrecomendation.model.Hotel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.HotelViewHolder> implements Serializable {

    private final Context context;
    private final LayoutInflater inflater;
    private View view;
    private HotelViewHolder hotelViewHolder;
    private List<Hotel> hotels;

    public RecommendationAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setHotels(Set<Hotel> lists) {
        this.hotels = new ArrayList<>(lists);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.hotelcard, parent, false);

        hotelViewHolder = new HotelViewHolder(view);

        return hotelViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HotelViewHolder holder, final int position) {
        Picasso
                .with(context)
                .load(Uri.parse(hotels.get(position).getImageUrl()))
                .into(holder.hotelImage);

        holder.hotelRatings.setText(hotels.get(position).getRatings());
        holder.tags.setText(hotels.get(position).getTags());
        holder.hotelName.setText(hotels.get(position).getName());
        holder.hotelViews.setText(hotels.get(position).getVisits() + "\nViews");
        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vis = Integer.valueOf(hotels.get(position).getVisits());
                hotels.get(position).setVisits(String.valueOf(++vis));
                setHotels(new HashSet<>(hotels));
                Intent i = new Intent(context, HotelInfo.class);
                i.putExtra("data", hotels.get(position));
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }


    public class HotelViewHolder extends RecyclerView.ViewHolder implements UpdateListener, Serializable {

        TextView hotelViews;
        ImageView hotelImage;
        TextView hotelRatings, hotelName;
        TextView tags;
        Button bookButton;

        public HotelViewHolder(@NonNull final View itemView) {
            super(itemView);
            hotelImage = itemView.findViewById(R.id.hotelImage);
            hotelRatings = itemView.findViewById(R.id.ratings);
            bookButton = itemView.findViewById(R.id.hotelBookButton);
            tags = itemView.findViewById(R.id.tagsList);
            hotelName = itemView.findViewById(R.id.hotelName);
            hotelViews = itemView.findViewById(R.id.hotelCardViews);

        }

        @Override
        public void update() {
            notifyItemChanged(getAdapterPosition());
        }
    }

    public interface UpdateListener extends Serializable {
        void update();
    }


}
