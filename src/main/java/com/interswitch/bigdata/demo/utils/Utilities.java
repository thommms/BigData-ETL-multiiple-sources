package com.interswitch.bigdata.demo.utils;

import com.interswitch.bigdata.demo.Configuration.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
@Singleton
public class Utilities {

    @Autowired
    ApplicationConfig config;

    public String buildKeyScheme() {
        StringBuilder builder = new StringBuilder();
        if (config.getChangeTrackingMode().equals("autoincrementing")) {
            builder.append(config.getAppNameds1()).append("_");
            builder.append(config.getJdbcHostNameds1()).append("_").append(config.getIdentityFieldNameds1());
        } else if (config.getChangeTrackingMode().equals("timestamp")) {
            builder.append(config.getAppNameds1()).append("_")
                    .append(config.getJdbcHostNameds1()).append("_")
                    .append(config.getTimeStampFieldNameds1());
        } else {

        }
        return builder.toString();
    }

    public String buildTImeSTampKeyScheme(){
        StringBuilder builder = new StringBuilder();
        builder.append(config.getAppNameds1()).append("_")
                .append(config.getJdbcHostNameds1()).append("_")
                .append(config.getTimeStampFieldNameds1());
        return builder.toString();

    }

    public String buildAutoIncrementingkeyScheme(){
        StringBuilder builder = new StringBuilder();
        builder.append(config.getAppNameds1()).append("_");
        builder.append(config.getJdbcHostNameds1()).append("_").append(config.ds1Template());
        return builder.toString();
    }


    public Date getDate(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:sss");
        Date  da= df.parse(date);
        return da;
    }

    public String getDate(Date date) throws ParseException {
        DateFormat df = new SimpleDateFormat(config.getJdbcDateFormat());
        String s = df.format(date);
        return s;
    }

    public Date incrementDateWithSeconds(Date theDateToIncrement, int numberOfSeconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(theDateToIncrement);
        calendar.add(Calendar.SECOND, numberOfSeconds);
        return calendar.getTime();
    }
}