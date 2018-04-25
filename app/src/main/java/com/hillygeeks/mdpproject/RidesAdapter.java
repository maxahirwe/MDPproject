package com.hillygeeks.mdpproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hillygeeks.mdpproject.DataClasses.Ride;
import com.hillygeeks.mdpproject.DataClasses.RideType;

import java.util.List;
//https://github.com/codepath/android_guides/wiki/Endless-Scrolling-with-AdapterViews-and-RecyclerView
public class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.ViewHolder> {
    private List<Ride> Rides;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txt_created_datetime,txt_depart_datetime,txt_origin,txt_destination,txt_provider,txt_cost_sharing;
        public ImageView left_arrow;
        public Button btn_offer,btn_take;
        public ViewHolder(View v) {
            super(v);
            txt_created_datetime= itemView.findViewById(R.id.txt_created_time);
            txt_depart_datetime= itemView.findViewById(R.id.txt_depart_time);
            txt_origin= itemView.findViewById(R.id.txt_origin);
            txt_destination= itemView.findViewById(R.id.txt_destination);
            txt_provider= itemView.findViewById(R.id.txt_provider_name);
            txt_cost_sharing= itemView.findViewById(R.id.txt_cost_sharing);
            left_arrow=itemView.findViewById(R.id.imageView_left_arrow);
            btn_offer=itemView.findViewById(R.id.btn_offer_ride);
            btn_take=itemView.findViewById(R.id.btn_take_ride);
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Ride ride=Rides.get(position);
        holder.txt_created_datetime.setText(ride.getSaved_dateTime());
        holder.txt_depart_datetime.setText(ride.getDepart_datetime());
        holder.txt_origin.setText(ride.getOrigin().getAddress());
        holder.txt_destination.setText(ride.getDestination().getAddress());
        holder.txt_provider.setText(ride.getCreator());
        if(ride.getShareCost()) { holder.txt_cost_sharing.setVisibility(View.VISIBLE);
        }else{
            holder.txt_cost_sharing.setVisibility(View.INVISIBLE);
        }
        if(ride.getReturning()){
            holder.left_arrow.setVisibility(View.VISIBLE);
        }else{
            holder.left_arrow.setVisibility(View.GONE);
        }
        if(ride.getType()== RideType.Request){
            holder.btn_take.setVisibility(View.GONE);
            holder.btn_offer.setVisibility(View.VISIBLE);
            holder.btn_offer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO:mark as booked,list as offered by this user and send notificationAa
                  OnCompleteListener<Void> completeListener=  new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("data","Changed Value");
                        }
                    };

                   Application.RidesRef.child(ride.id).child("type").setValue(RideType.Booking).addOnCompleteListener(completeListener);
                   Application.RidesRef.child(ride.id).child("booked").setValue(true).addOnCompleteListener(completeListener);
                   Application.RidesRef.child(ride.id).child("provider").setValue(Application.username).addOnCompleteListener(completeListener);

                }
            });
        }else if (ride.getType()== RideType.Offer){
            holder.btn_offer.setVisibility(View.GONE);
            holder.btn_take.setVisibility(View.VISIBLE);
            holder.btn_take.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: mark as booked,listed as given and send notification

                    OnCompleteListener<Void> completeListener=  new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("data","Changed Value");
                        }
                    };
                    Application.RidesRef.child(ride.id).child("type").setValue(RideType.Booking).addOnCompleteListener(completeListener);
                    Application.RidesRef.child(ride.id).child("booked").setValue(true).addOnCompleteListener(completeListener);
                    Application.RidesRef.child(ride.id).child("client").setValue(Application.username).addOnCompleteListener(completeListener);


                }
            });
        }else{
            holder.btn_offer.setVisibility(View.GONE);
            holder.btn_take.setVisibility(View.GONE);
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