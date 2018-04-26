package com.hillygeeks.mdpproject;

import android.content.Context;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hillygeeks.mdpproject.DataClasses.Ride;
import com.hillygeeks.mdpproject.DataClasses.RideType;
import com.hillygeeks.mdpproject.DataClasses.User;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hillygeeks.mdpproject.MessagingService.NotificationPayload;
import com.hillygeeks.mdpproject.MessagingService.NotificationSenderAPI;


import java.util.List;
//https://github.com/codepath/android_guides/wiki/Endless-Scrolling-with-AdapterViews-and-RecyclerView
public class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.ViewHolder> {
    private List<Ride> Rides;

    private Context context;



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
    public RidesAdapter(List<Ride> myDataset, Context context) {
        Rides = myDataset;
        this.context=context;
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
        final User creator_user=new User();
       Application.UsersRef.child(ride.getCreator()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                creator_user.setName(user.getName());
                creator_user.setDevice_id(user.getDevice_id());
                Log.d("user fetch","ride:"+ride.toString()+"user:"+user.toString());
                holder.txt_provider.setText(creator_user.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        holder.txt_created_datetime.setText(ride.getSaved_dateTime());
        holder.txt_depart_datetime.setText(ride.getDepart_datetime());
        holder.txt_origin.setText(ride.getOrigin().getAddress());
        holder.txt_destination.setText(ride.getDestination().getAddress());
        if(ride.getShareCost()) { holder.txt_cost_sharing.setVisibility(View.VISIBLE);
        }
        else{
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

                    Application.UsersRef.child(ride.getCreator()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            User user = dataSnapshot.getValue(User.class);
                            String from = FirebaseInstanceId.getInstance().getToken();
                            String to=user.getDevice_id();
                            String rideType = context.getResources().getString(R.string.rideConformer);
                            String message=context.getResources().getString(R.string.notificationMessage2);
                            Application.SendNotificaion(from, to, rideType, message,  context);

                            //TODO:mark as booked,list as offered by this user and send notification
                            OnCompleteListener<Void> completeListener=  new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("data","Changed Value");
                                }
                            };
                            Application.RidesRef.child(ride.id).child("type").setValue(RideType.Booking).addOnCompleteListener(completeListener);
                            Application.RidesRef.child(ride.id).child("booked").setValue(true).addOnCompleteListener(completeListener);
                            Application.RidesRef.child(ride.id).child("provider").setValue(Application.user.userid).addOnCompleteListener(completeListener);
                            //Notification should be sent Herento the client

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                }
            });
        }else if (ride.getType()== RideType.Offer){
            holder.btn_offer.setVisibility(View.GONE);
            holder.btn_take.setVisibility(View.VISIBLE);
            holder.btn_take.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Application.UsersRef.child(ride.getCreator()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            String from = FirebaseInstanceId.getInstance().getToken();
                            String to=user.getDevice_id();
                            String rideType = context.getResources().getString(R.string.rideRequester);
                            String message=context.getResources().getString(R.string.notificationMessage1);
                            Application.SendNotificaion(from, to, rideType, message,  context);

                            //TODO: mark as booked,listed as given and send notification
                            OnCompleteListener<Void> completeListener=  new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("data","Changed Value");
                                }
                            };
                            Application.RidesRef.child(ride.id).child("type").setValue(RideType.Booking).addOnCompleteListener(completeListener);
                            Application.RidesRef.child(ride.id).child("booked").setValue(true).addOnCompleteListener(completeListener);
                            Application.RidesRef.child(ride.id).child("client").setValue(Application.user.userid).addOnCompleteListener(completeListener);
                            //Notification should be sent Here

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



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