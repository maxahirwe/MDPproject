package com.hillygeeks.mdpproject;

public enum ETabs {
    FIND_RIDE (0, "Find Ride"),
    POST_RIDE (1,"Post Ride"),
    REQ_RIDE (2, "Request Ride");

    private int tabNumber;
    private String tabName;

    ETabs(int tabNumber, String tabName) {
        this.tabNumber=tabNumber;
        this.tabName = tabName;
    }

    public int getTabNumber() {
        return tabNumber;
    }

    public static ETabs getTabFromInt(int tabNumber){
        switch (tabNumber){
            case 0:
                return FIND_RIDE;
            case 1:
                return POST_RIDE;
            case 2:
                return REQ_RIDE;
        }
        return null;
    }

    public String getTabName() {
        return tabName;
    }
}
