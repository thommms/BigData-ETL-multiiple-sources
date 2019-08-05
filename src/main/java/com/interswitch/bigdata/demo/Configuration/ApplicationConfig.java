package com.interswitch.bigdata.demo.Configuration;

import com.interswitch.bigdata.demo.Bigdata_ETL_datasources;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig implements Serializable{

    @Value("${jdbc.port}")
    private String jdbcPort;
    @Value("${jdbc.userName}")
    private String jdbcUserName;
    @Value("${jdbc.password}")
    private String jdbcPassword;
    @Value("${jdbc.batchsize}")
    private int jdbcBatchSize;
    @Value("${mapr.lastBatchId}")
    private long currentCheckPoint;
    @Value("${jdbc.lastTimeStamp}")
    private String currentTimeStamp;
    @Value("${jdbc.dateformat}")
    private String dateFormat;
    @Value("${mapr.progess_tracking}")
    private String checkPointTableName;
    @Value("${jdbc.timeInterval}")
    private int timeIntervalInSeconds;
    @Value("${jdbc.changetrackingmode}")
    private String changeTrackingMode;
    @Value("${mapr.saveToFs}")
    private boolean saveToFileStorage;
    @Value("${mapr.saveToDB}")
    private boolean saveToDB;
    @Value("${mapr.pushToStream}")
    private boolean pushToStream;


    @Value("${app.ds1.name}")
    private String appNameds1;
    @Value("${app.ds2.name}")
    private String appNameds2;
    @Value("${app.ds3.name}")
    private String appNameds3;
    @Value("${app.ds4.name}")
    private String appNameds4;

    @Value("${jdbc.ds1.hostname}")
    private String jdbcHostNameds1;
    @Value("${jdbc.ds2.hostname}")
    private String jdbcHostNameds2;
    @Value("${jdbc.ds3.hostname}")
    private String jdbcHostNameds3;
    @Value("${jdbc.ds4.hostname}")
    private String jdbcHostNameds4;

    @Value("${jdbc.ds1.databaseName}")
    private String jdbcDatabaseNameds1;
    @Value("${jdbc.ds2.databaseName}")
    private String jdbcDatabaseNameds2;
    @Value("${jdbc.ds3.databaseName}")
    private String jdbcDatabaseNameds3;
    @Value("${jdbc.ds4.databaseName}")
    private String jdbcDatabaseNameds4;

    @Value("${jdbc.ds1.tableName}")
    private String jdbcTableNameds1;
    @Value("${jdbc.ds2.tableName}")
    private String jdbcTableNameds2;
    @Value("${jdbc.ds3.tableName}")
    private String jdbcTableNameds3;
    @Value("${jdbc.ds4.tableName}")
    private String jdbcTableNameds4;

    @Value("${jdbc.ds1.identityfieldname}")
    private String identityFieldNameds1;
    @Value("${jdbc.ds2.identityfieldname}")
    private String identityFieldNameds2;
    @Value("${jdbc.ds3.identityfieldname}")
    private String identityFieldNameds3;
    @Value("${jdbc.ds4.identityfieldname}")
    private String identityFieldNameds4;

    @Value("${mapr.ds1.streamingtopic}")
    private String streamingTargetTopicds1;
    @Value("${mapr.ds2.streamingtopic}")
    private String streamingTargetTopicds2;
    @Value("${mapr.ds3.streamingtopic}")
    private String streamingTargetTopicds3;
    @Value("${mapr.ds4.streamingtopic}")
    private String streamingTargetTopicds4;

    @Value("${jdbc.ds1.timestampfieldname}")
    private String timeStampFieldNameds1;
    @Value("${jdbc.ds2.timestampfieldname}")
    private String timeStampFieldNameds2;
    @Value("${jdbc.ds3.timestampfieldname}")
    private String timeStampFieldNameds3;
    @Value("${jdbc.ds4.timestampfieldname}")
    private String timeStampFieldNameds4;

    @Value("${mapr.ds1.fileStoragePath}")
    private String fileStoragePathds1;
    @Value("${mapr.ds2.fileStoragePath}")
    private String fileStoragePathds2;
    @Value("${mapr.ds3.fileStoragePath}")
    private String fileStoragePathds3;
    @Value("${mapr.ds4.fileStoragePath}")
    private String fileStoragePathds4;

    @Value("${jdbc.ds1.identityfieldname}")
    private String tableIdentityFieldNameds1;
    @Value("${jdbc.ds2.identityfieldname}")
    private String tableIdentityFieldNameds2;
    @Value("${jdbc.ds3.identityfieldname}")
    private String tableIdentityFieldNameds3;
    @Value("${jdbc.ds4.identityfieldname}")
    private String tableIdentityFieldNameds4;

    @Value("${mapr.ds1.dbStoragePath}")
    private String dbStoragePathds1;
    @Value("${mapr.ds2.dbStoragePath}")
    private String dbStoragePathds2;
    @Value("${mapr.ds3.dbStoragePath}")
    private String dbStoragePathds3;
    @Value("${mapr.ds4.dbStoragePath}")
    private String dbStoragePathds4;

    public String getChangeTrackingMode() {
        return changeTrackingMode;
    }
    public boolean getSaveToFs(){
        return this.saveToFileStorage;
    }
    public long getBatchSize() {return jdbcBatchSize;}
    public String getJDBCPassword() {
        return jdbcPassword;
    }
    public String getJDBCUserName() {
        return jdbcUserName;
    }
    public long getCurrentCheckPoint() {
        return currentCheckPoint;
    }
    public String getCurrentTimeStamp(){
        return currentTimeStamp;
    }
    public String getJdbcDateFormat(){
        return dateFormat;
    }
    public String getProgressTrackingTableName() {
        return checkPointTableName;
    }
    public int getTimeIntervalInSeconds() {
        return timeIntervalInSeconds;
    }
    public boolean isSaveToDB() {
        return saveToDB;
    }
    public boolean isPushToStream() {
        return pushToStream;
    }



    public String getAppNameds1(){
        return appNameds1;
    }
    public String getAppNameds2(){
        return appNameds2;
    }
    public String getAppNameds3(){
        return appNameds3;
    }
    public String getAppNameds4(){
        return appNameds4;
    }

    public String getJdbcTableNameds1(){
        return jdbcTableNameds1;
    }
    public String getJdbcTableNameds2(){
        return jdbcTableNameds2;
    }
    public String getJdbcTableNameds3(){
        return jdbcTableNameds3;
    }
    public String getJdbcTableNameds4(){
        return jdbcTableNameds4;
    }

    public String getJdbcHostNameds1(){
        return jdbcHostNameds1;
    }
    public String getJdbcHostNameds2(){
        return jdbcHostNameds2;
    }
    public String getJdbcHostNameds3(){
        return jdbcHostNameds3;
    }
    public String getJdbcHostNameds4(){
        return jdbcHostNameds4;
    }

    public String getStreamingTargetTopicds1() {
        return streamingTargetTopicds1;
    }
    public String getStreamingTargetTopicds2() {
        return streamingTargetTopicds2;
    }
    public String getStreamingTargetTopicds3() {
        return streamingTargetTopicds3;
    }
    public String getStreamingTargetTopicds4() {
        return streamingTargetTopicds4;
    }

    public String getTimeStampFieldNameds1() {
        return timeStampFieldNameds1;
    }
    public String getTimeStampFieldNameds2() {
        return timeStampFieldNameds2;
    }
    public String getTimeStampFieldNameds3() {
        return timeStampFieldNameds3;
    }
    public String getTimeStampFieldNameds4() {
        return timeStampFieldNameds4;
    }

    public String getDbStoragePathds1() {
        try {
            return mapper().getObject().getProperty("mapr.ds1.dbStoragePath");
        } catch (IOException e) {
            return dbStoragePathds1;
        }
    }
    public String getDbStoragePathds2() {
        try {
            return mapper().getObject().getProperty("mapr.ds2.dbStoragePath");
        } catch (IOException e) {
            return dbStoragePathds2;
        }
    }
    public String getDbStoragePathds3() {
        try {
            return mapper().getObject().getProperty("mapr.ds3.dbStoragePath");
        } catch (IOException e) {
            return dbStoragePathds3;
        }
    }
    public String getDbStoragePathds4() {
        try {
            return mapper().getObject().getProperty("mapr.ds4.dbStoragePath");
        } catch (IOException e) {
            return dbStoragePathds4;
        }
    }


    public String getTableIdentityFieldNameds1() {
        try {
            return mapper().getObject().getProperty("jdbc.ds1.identityfieldname");
        } catch (IOException e) {
            return tableIdentityFieldNameds1;
        }
    }
    public String getTableIdentityFieldNameds2() {
        try {
            return mapper().getObject().getProperty("jdbc.ds2.identityfieldname");
        } catch (IOException e) {
            return tableIdentityFieldNameds2;
        }
    }
    public String getTableIdentityFieldNameds3() {
        try {
            return mapper().getObject().getProperty("jdbc.ds3.identityfieldname");
        } catch (IOException e) {
            return tableIdentityFieldNameds3;
        }
    }
    public String getTableIdentityFieldNameds4() {
        try {
            return mapper().getObject().getProperty("jdbc.old_webpay.identityfieldname");
        } catch (IOException e) {
            return tableIdentityFieldNameds4;
        }
    }


//    public String getJdBcConnectionStringVtucare(){
//        return String.format(
//                "jdbc:sqlserver://%s:%s;databaseName=%s",
//                jdbcHostNameVtucare, jdbcPort, jdbcDatabaseNameVtucare);
//    }
//    public String getJdBcConnectionStringQuickteller(){
//        return String.format(
//                "jdbc:sqlserver://%s:%s;databaseName=%s",
//                jdbcHostNameQuickteller, jdbcPort, jdbcDatabaseNameQuickteller);
//    }

    public String getIdentityFieldNameds1() {
        try {
            return mapper().getObject().getProperty("jdbc.ds1.identityfieldname");
        } catch (IOException e) {
            return identityFieldNameds1;
        }
    }
    public String getIdentityFieldNameds2() {
        try {
            return mapper().getObject().getProperty("jdbc.ds2.identityfieldname");
        } catch (IOException e) {
            return identityFieldNameds2;
        }
    }
    public String getIdentityFieldNameds3() {
        try {
            return mapper().getObject().getProperty("jdbc.ds3.identityfieldname");
        } catch (IOException e) {
            return identityFieldNameds3;
        }
    }
    public String getIdentityFieldNameds4() {
        try {
            return mapper().getObject().getProperty("jdbc.ds4.identityfieldname");
        } catch (IOException e) {
            return identityFieldNameds4;
        }
    }


    public String getJdBcConnectionStringds1() {
        //return jdBcConnectionStringVtucare;
        return String.format(
                "jdbc:sqlserver://%s:%s;databaseName=%s",
                getJdbcHostNameds1(), jdbcPort, jdbcDatabaseNameds1);
    }
    public String getJdBcConnectionStringds2() {
        //return jdBcConnectionStringVtucare;
        return String.format(
                "jdbc:sqlserver://%s:%s;databaseName=%s",
                jdbcHostNameds2, jdbcPort, jdbcDatabaseNameds2);
    }
    public String getJdBcConnectionStringds3() {
        //return jdBcConnectionStringVtucare;
        return String.format(
                "jdbc:sqlserver://%s:%s;databaseName=%s",
                jdbcHostNameds3, jdbcPort, jdbcDatabaseNameds3);
    }
    public String getJdBcConnectionStringds4() {
        //return jdBcConnectionStringVtucare;
        return String.format(
                "jdbc:sqlserver://%s:%s;databaseName=%s",
                jdbcHostNameds4, jdbcPort, jdbcDatabaseNameds4);
    }

    @Bean
    public static PropertiesFactoryBean mapper() throws IOException {
        PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
        factoryBean.setLocation(new ClassPathResource("application.properties"));
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

    @Bean
    public static KafkaProducer kafkaProducer(){
           HashMap<String, Object> properties = new HashMap<>();
            properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.put("acks", "all");
            KafkaProducer<String, Object> producer =
                    new KafkaProducer<>(properties);
            return producer;
    }

    @Bean
    public Logger loger(){
        Logger logger = LoggerFactory.getLogger(Bigdata_ETL_datasources.class);
        return logger;
    }
    public String getDriverClassName(){
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }


    @Bean
    public JdbcTemplate ds1Template() {
        DriverManagerDataSource ds1 = new DriverManagerDataSource();
        ds1.setDriverClassName(getDriverClassName());
        ds1.setUrl(getJdBcConnectionStringds1());
        ds1.setUsername(getJDBCUserName());
        ds1.setPassword(getJDBCPassword());
        return new JdbcTemplate(ds1);
    }

    @Bean
    public JdbcTemplate ds2Template() {
        DriverManagerDataSource ds2 = new DriverManagerDataSource();
        ds2.setDriverClassName(getDriverClassName());
        ds2.setUrl(getJdBcConnectionStringds2());
        ds2.setUsername(getJDBCUserName());
        ds2.setPassword(getJDBCPassword());
        return new JdbcTemplate(ds2);
    }

    @Bean
    public JdbcTemplate ds3Template() {
        DriverManagerDataSource ds3 = new DriverManagerDataSource();
        ds3.setDriverClassName(getDriverClassName());
        ds3.setUrl(getJdBcConnectionStringds3());
        ds3.setUsername(getJDBCUserName());
        ds3.setPassword(getJDBCPassword());
        return new JdbcTemplate(ds3);
    }

    @Bean
    public JdbcTemplate ds4Template() {
        DriverManagerDataSource ds4 = new DriverManagerDataSource();
        ds4.setDriverClassName(getDriverClassName());
        ds4.setUrl(getJdBcConnectionStringds4());
        ds4.setUsername(getJDBCUserName());
        ds4.setPassword(getJDBCPassword());
        return new JdbcTemplate(ds4);
    }

}