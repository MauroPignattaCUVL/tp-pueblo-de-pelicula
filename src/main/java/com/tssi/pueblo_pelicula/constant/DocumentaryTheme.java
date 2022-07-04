package com.tssi.pueblo_pelicula.constant;

public enum DocumentaryTheme {
    BIOGRAPHY, WILD_LIFE, ECOLOGY, HISTORICAL, SOCIAL;

    public static DocumentaryTheme fromValue(String value) {
        for (DocumentaryTheme documentaryTheme : values()) {
            if (documentaryTheme.name().equalsIgnoreCase(value)) {
                return documentaryTheme;
            }
        }

        return null;
    }
}
