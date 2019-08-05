package com.interswitch.bigdata.demo.utils;

import com.interswitch.bigdata.demo.Configuration.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class Utilitiesds3 {
    @Autowired
    ApplicationConfig config;

    public String buildKeyScheme() {
        StringBuilder builder = new StringBuilder();
        if (config.getChangeTrackingMode().equals("autoincrementing")) {
            builder.append(config.getAppNameds3()).append("_");
            builder.append(config.getJdbcHostNameds3()).append("_").append(config.getIdentityFieldNameds3());
        } else if (config.getChangeTrackingMode().equals("timestamp")) {
            builder.append(config.getAppNameds3()).append("_")
                    .append(config.getJdbcHostNameds3()).append("_")
                    .append(config.getTimeStampFieldNameds3());
        } else {

        }
        return builder.toString();
    }

    public String buildTImeSTampKeyScheme(){
        StringBuilder builder = new StringBuilder();
        builder.append(config.getAppNameds3()).append("_")
                .append(config.getJdbcHostNameds3()).append("_")
                .append(config.getTimeStampFieldNameds3());
        return builder.toString();

    }

    public String buildAutoIncrementingkeyScheme(){
        StringBuilder builder = new StringBuilder();
        builder.append(config.getAppNameds3()).append("_");
        builder.append(config.getJdbcHostNameds3()).append("_").append(config.getIdentityFieldNameds3());
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
