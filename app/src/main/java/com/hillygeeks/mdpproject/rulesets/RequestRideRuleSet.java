package com.hillygeeks.mdpproject.rulesets;

import android.support.v4.app.Fragment;

import com.hillygeeks.mdpproject.DataClasses.RequestRide;
import com.hillygeeks.mdpproject.RequestRideFragment;

public class RequestRideRuleSet implements RuleSet {
    RequestRideFragment requestRideFragment;

    @Override
    public Object applyRules(Fragment ob) throws RuleException {
        requestRideFragment=(RequestRideFragment)ob;
        return new RequestRide();
    }
}
