package com.watchstore.enums;

public enum WatchBrand {
    ROLEX("Rolex"),
    OMEGA("Omega"),
    SEIKO("Seiko"),
    APPLE("Apple"),
    GARMIN("Garmin"),
    TITAN("Titan"),
    PATEK_PHILIPPE("Patek Philippe"),
    CASIO("Casio");

    private final String displayName;

    WatchBrand(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
