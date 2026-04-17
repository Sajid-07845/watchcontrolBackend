package com.watchstore.enums;

public enum WatchCategory {
    SMART_WATCH("Smart Watch"),
    SPORTS("Sports"),
    LUXURY("Luxury"),
    CLASSIC("Classic"),
    ANALOG("Analog"),
    CHRONOGRAPH("Chronograph");

    private final String displayName;

    WatchCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
