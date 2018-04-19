package com.hillygeeks.mdpproject.rulesets;

import android.support.v4.app.Fragment;

public interface RuleSet {
    Object applyRules(Fragment ob) throws RuleException;
}

