package com.project.bigdata.demo.Serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Map;


@Component(value = "json")
public class JsonRowSerializer implements RowSerializer{
    @Override
    public Object serialize(Map row) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new DateSerializer()).create();
        String gsonString = gson.toJson(row);
        return gsonString;
    }
}
