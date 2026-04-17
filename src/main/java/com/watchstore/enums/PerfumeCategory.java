package com.watchstore.enums;

public enum PerfumeCategory {
    EAU_DE_PARFUM("Eau de Parfum"),
    EAU_DE_TOILETTE("Eau de Toilette"),
    EAU_DE_COLOGNE("Eau de Cologne"),
    PERFUME_OIL("Perfume Oil"),
    FLORAL("Floral"),
    ORIENTAL("Oriental"),
    WOODY("Woody"),
    FRESH("Fresh");

    private final String displayName;

    PerfumeCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
