package com.hillygeeks.mdpproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hillygeeks.mdpproject.DataClasses.Ride;

import java.util.List;
//https://github.com/codepath/android_guides/wiki/Endless-Scrolling-with-AdapterViews-and-RecyclerView
public class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.ViewHolder> {
    private List<Ride> Rides;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txt_datetime,txt_origin,txt_destination,txt_provider,txt_cost_sharing;
        public ImageView left_arrow;
        public ViewHolder(View v) {
            super(v);
            txt_datetime= itemView.findViewById(R.id.txt_time);
            txt_origin= itemView.findViewById(R.id.txt_origin);
            txt_destination= itemView.findViewById(R.id.txt_destination);
            txt_provider= itemView.findViewById(R.id.txt_provider_name);
            txt_cost_sharing= itemView.findViewById(R.id.txt_cost_sharing);
            left_arrow=itemView.findViewById(R.id.imageView_left_arrow);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RidesAdapter(List<Ride> myDataset) {
        Rides = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ride_view, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Ride ride=Rides.get(position);
        holder.txt_datetime.setText(ride.getDateTime());
        holder.txt_origin.setText(ride.getOrigin().getAddress());
        holder.txt_destination.setText(ride.getDestination().getAddress());
        holder.txt_provider.setText(ride.getProvider());
        if(ride.getShareCost())  holder.txt_cost_sharing.setVisibility(View.VISIBLE);
        if(ride.getReturning()){
            holder.left_arrow.setVisibility(View.VISIBLE);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return Rides.size();
    }

    public List<Ride> getRides() {
        return Rides;
    }

    public void setRides(List<Ride> rides) {
        Rides = rides;
    }
}