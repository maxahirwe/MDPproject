package com.hillygeeks.mdpproject.rulesets;

import android.support.v4.app.Fragment;

import com.hillygeeks.mdpproject.PostRideFragment;
import com.hillygeeks.mdpproject.RequestRideFragment;

import java.util.HashMap;

final public class RuleSetFactory {
    private RuleSetFactory(){}
    static HashMap<Class<? extends Fragment>, RuleSet> map = new HashMap<>();

    static {
        map.put(PostRideFragment.class, new PostRideRuleSet());
        map.put(RequestRideFragment.class, new RequestRideRuleSet());
    }
    public static RuleSet getRuleSet(Fragment fragment) {
        Class<? extends Fragment> cl = fragment.getClass();
        if(!map.containsKey(cl)) {
            throw new IllegalArgumentException(
                    "No RuleSet found for this Fragment");
        }
        return map.get(cl);
    }

}

