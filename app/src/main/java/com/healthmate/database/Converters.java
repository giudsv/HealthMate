package com.healthmate.database;

import androidx.room.TypeConverter;

import java.time.Instant;

public final class Converters {
    private Converters() {}

    @TypeConverter
    public static String fromInstant(Instant instant) {
        return instant.toString();
    }

    @TypeConverter
    public static Instant toInstant(String date) {
        return Instant.parse(date);
    }
}
