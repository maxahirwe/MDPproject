package com.hillygeeks.mdpproject.rulesets;

import android.support.v4.app.Fragment;

import com.hillygeeks.mdpproject.DataClasses.PostRide;
import com.hillygeeks.mdpproject.PostRideFragment;
import com.hillygeeks.mdpproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostRideRuleSet implements RuleSet{
    private PostRideFragment postRideFragment;

    @Override
    public Object applyRules(Fragment ob) throws RuleException {
        postRideFragment=(PostRideFragment) ob;
        String fromLocation=validateFromLocation();
        String toLocation=validateToLocation();
        Double price = validatePrice();
        Integer totalSeats=validateTotalSeats();
        Date depDate=validateDepDateTime();
        validateVehicle();
        String description = validateDescription();

        PostRide postRide=new PostRide();
        postRide.setDepDateTime(depDate);
        postRide.setToLocation(toLocation);
        postRide.setFromLocation(fromLocation);
        postRide.setDescription(description);
        postRide.setPrice(price);
        postRide.setTotalSeats(totalSeats);

        return postRide;
    }

    private String validateDescription() {
        return postRideFragment.getDescription();
    }

    private Date validateDepDateTime() throws RuleException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(postRideFragment.getResources().getString(R.string.fmtDateTime));
        try {
            return dateFormat.parse(postRideFragment.getDepDateTime());
        } catch (ParseException e) {
            throw new RuleException(e.getMessage());
        }
    }

    private void validateVehicle() throws RuleException{
    }

    private Integer validateTotalSeats() throws RuleException{
        return Integer.valueOf(postRideFragment.getTotalSeats());
    }

    private String validateFromLocation() throws RuleException{
        return postRideFragment.getFromLocation();
    }

    private String validateToLocation() throws RuleException{
        return postRideFragment.getToLocation();
    }

    private Double validatePrice() throws RuleException{
        return Double.valueOf(postRideFragment.getPrice());
    }

}
