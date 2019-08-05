package com.interswitch.bigdata.demo.utils;

import org.ojai.store.Connection;
import org.ojai.store.DriverManager;

public  class ConnectionUtils {

    public static Connection connection(){
        String connectionUrl = "ojai:mapr:";
        Connection connection = DriverManager.getConnection(connectionUrl);
        return connection;
    }
}
