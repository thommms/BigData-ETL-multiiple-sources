package com.interswitch.bigdata.demo.dataaccess;

import com.interswitch.bigdata.demo.Configuration.ApplicationConfig;
import com.interswitch.bigdata.demo.utils.Utilities;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class SQLServerDataProvider {


    @Autowired
    Logger logger;

    @Autowired
    ApplicationConfig applicationConfig;

//    @Autowired
//    JdbcTemplate vtuCareTemplate;

//    @Autowired
//    JdbcTemplate jdbcTemplate;


    @Autowired
    Utilities utilities;

    String getSqlQuery(long lowerBound, long upperBound){
        String tableName = applicationConfig.getJdbcTableNameds1();
        String identityTableName = applicationConfig.getIdentityFieldNameds1();
//        lowerBound=applicationConfig.getCurrentCheckPoint();
//        upperBound=lowerBound+applicationConfig.getBatchSize();
        String query = String.format("(Select * from %s with (nolock) where %s between %s and %s)", tableName, identityTableName, lowerBound, upperBound);
        return query;
    }
//    String getSqlQuery(long lowerbound, long upperBound, Date date){
//
//        String tableName = applicationConfig.getTableName();
//        String identityTableName = applicationConfig.getIdentityFieldName();
//        String timeStampColumnName = applicationConfig.getTimeStampFieldName();
//        int timeInterval = applicationConfig.getTimeIntervalInSeconds();
//
//        String firstDate = null;
//        try {
//            firstDate = utilities.getDate(date);
//        } catch (ParseException e) {
//            logger.error(e.getMessage());
//        }
//        Date secondDate = utilities.incrementDateWithSeconds(date, timeInterval);
//        String cutOffDate = null;
//        try {
//            cutOffDate = utilities.getDate(secondDate);
//        } catch (ParseException e) {
//            logger.error(e.getMessage());
//        }
//        StringBuilder builder = new StringBuilder();
//        builder.append(String.format("(SELECT * FROM %s with (nolock)", tableName));
//        builder.append(String.format("WHERE %s < CURRENT_TIMESTAMP AND (( %s = '%s') ", timeStampColumnName, timeStampColumnName, cutOffDate));
//        builder.append(String.format("OR %s < '%s') AND %s > '%s') AS T", timeStampColumnName, cutOffDate, timeStampColumnName, firstDate));
////        String query = String.format("(Select * from %s where (%s between %s and %s) " +
////                " AND (%s BETWEEN %s AND %s)) AS T", tableName, identityTableName, lowerbound, upperBound
////        , timeStampColumnName, date, secondDate);
//        return builder.toString();
//    }
//
//    String getSqlQuery (Date date){
//        String tableName = applicationConfig.getTableName();
//        String timeStampColumnName = applicationConfig.getTimeStampFieldName();
//        int timeInterval = applicationConfig.getTimeIntervalInSeconds();
//        Date secondDate = utilities.incrementDateWithSeconds(date, timeInterval);
//        String query = String.format("(Select * from %s with (nolock) where %s between %s and %s) AS T", tableName, timeStampColumnName, date, secondDate);
//        return query;
//    }

    String getLookAheadQuery(long lowerBound){
        String tableName = applicationConfig.getJdbcTableNameds1();
        String identityFieldName = applicationConfig.getIdentityFieldNameds2();
        long upperbound = applicationConfig.getCurrentCheckPoint() + applicationConfig.getBatchSize();
//        lowerBound=applicationConfig.getCurrentCheckPoint();
//        long upperbound=applicationConfig.getBatchSize()+applicationConfig.getCurrentCheckPoint();

        String query = String.format("SELECT TOP(1) * FROM %s with (nolock) where %s > %s", tableName,
                upperbound,lowerBound);
        System.out.println(query);
        return query;
    }

//    String getLookAheadQuery(Date date){
//        String cutOffDate = null;
//        try {
//            cutOffDate = utilities.getDate(date);
//        } catch (ParseException e) {
//            logger.error(e.getMessage());
//        }
//        String tableName = applicationConfig.getJdbcTableNameVtucare();
//        String timeStampColumnName = applicationConfig.getTimeStampFieldNameVtucare();
//        String query = String.format("SELECT TOP(1) * FROM %s with (NOLOCK) WHERE %s > '%s' ORDER BY %s ASC", tableName, timeStampColumnName, cutOffDate, timeStampColumnName);
//        return query;
//    }

//    public List<Map<String, Object>> query(long lastCheckpoint, Date cutOffDate){
//
//        long newCheckpoint = lastCheckpoint + applicationConfig.getBatchSize();
//        String query = getSqlQuery(lastCheckpoint, newCheckpoint);
//        List<Map<String, Object>> result = applicationConfig.vtuCareTemplate().queryForList(query);
//       return result;
//    }

    public List<Map<String, Object>> query(long lastCheckpoint) {

        long newCheckpoint = applicationConfig.getCurrentCheckPoint() + applicationConfig.getBatchSize();
//       lastCheckpoint=applicationConfig.getCurrentCheckPoint();
        logger.info(String.format("Our New Cutoffpoint is %d", newCheckpoint));
        logger.info(String.format("Our New Cutoffpoint is %d", lastCheckpoint));
        List<Map<String, Object>> result = applicationConfig.ds1Template().queryForList(getSqlQuery(lastCheckpoint,newCheckpoint));
        logger.info(String.format("Returned %s Rows", result.size()));
        return result;
    }

    public long recordExists(long lastCheckPoint) throws Exception{
//        lastCheckPoint= applicationConfig.getCurrentCheckPoint();
        logger.info("Looking Ahead For Records That Exist Ahead of This Checkpoint");
        String recordExistsQuery = getLookAheadQuery(lastCheckPoint);
        long nextPostTranId = 0L;
        nextPostTranId = applicationConfig.ds1Template().queryForObject(recordExistsQuery, new RowMapper<Long>() {
            @Nullable
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                Long nextPostTranId = resultSet.getLong(applicationConfig.getIdentityFieldNameds1());
                return nextPostTranId;
            }
        });
        return nextPostTranId;
    }

//        public boolean recordExists(Date lastTimeStamp) throws SQLException {
//             boolean result = false;
//            logger.info("Looking Ahead For Records That Exist Ahead of This Checkpoint");
//            String recordExistsQuery = getLookAheadQuery(lastTimeStamp);
//
//            result = applicationConfig.vtuCareTemplate().queryForObject(recordExistsQuery, new RowMapper<Boolean>() {
//                @Nullable
//                @Override
//                public Boolean mapRow(ResultSet resultSet, int i) throws SQLException {
//                    Date nextTImeStamp = resultSet.getTimestamp(applicationConfig.getTimeStampFieldName());
//                    return nextTImeStamp != null;
//                };
//            });
//            return result;
//        }

}
