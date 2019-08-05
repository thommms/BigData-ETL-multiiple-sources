package com.interswitch.bigdata.demo;

import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.interswitch.bigdata.demo.TaskRunner.TaskRunner;
import com.interswitch.bigdata.demo.TaskRunner.TaskRunnerds2;
import com.interswitch.bigdata.demo.TaskRunner.TaskRunnerds3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Bigdata_ETL_datasources implements CommandLineRunner {

    @Autowired
    TaskRunner taskRunner;
    @Autowired
    TaskRunnerds3 taskRunnerds3;
    @Autowired
    TaskRunnerds2 taskRunnerds2;

    public static void main(String[] args) {
        SpringApplication.run(Bigdata_ETL_datasources.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Set<Service> services= new HashSet<>();
        services.add(taskRunner);
        services.add(taskRunnerds2);
        services.add(taskRunnerds3);
        ServiceManager manager = new ServiceManager(services);
        manager.startAsync();
    }
}
