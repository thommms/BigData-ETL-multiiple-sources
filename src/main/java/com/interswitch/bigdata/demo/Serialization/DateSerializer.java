package com.interswitch.bigdata.demo.Serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Timestamp;

public class DateSerializer extends TypeAdapter<Timestamp> {
    @Override
    public void write(JsonWriter out, Timestamp value) throws IOException {
        if (value == null)
            out.nullValue();
        else
            out.value(value.getTime());
    }

    @Override
    public Timestamp read(JsonReader in) throws IOException {

            return null;
    }
}