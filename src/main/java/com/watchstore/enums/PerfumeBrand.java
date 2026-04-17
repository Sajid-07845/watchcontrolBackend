package com.watchstore.enums;

public enum PerfumeBrand {
    CHANEL("Chanel"),
    DIOR("Dior"),
    VERSACE("Versace"),
    GUCCI("Gucci"),
    ARMANI("Armani"),
    TOM_FORD("Tom Ford"),
    BURBERRY("Burberry"),
    CALVIN_KLEIN("Calvin Klein"),
    HUGO_BOSS("Hugo Boss"),
    CAROLINA_HERRERA("Carolina Herrera");

    private final String displayName;

    PerfumeBrand(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
