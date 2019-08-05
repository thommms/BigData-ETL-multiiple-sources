package com.interswitch.bigdata.demo.dataaccess;

import com.interswitch.bigdata.demo.Configuration.ApplicationConfig;
import com.interswitch.bigdata.demo.utils.Utilitiesds2;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class SQLServerDataProviderds2 {


    @Autowired
    Logger logger;

    @Autowired
    ApplicationConfig applicationConfig;


    @Autowired
    Utilitiesds2 utilities;

    String getSqlQuery(long lowerBound, long upperBound){
        String tableName = applicationConfig.getJdbcTableNameds2();
        String identityTableName = applicationConfig.getIdentityFieldNameds2();
        String query = String.format("(Select * from %s with (nolock) where %s between %s and %s)", tableName, identityTableName, lowerBound, upperBound);
        return query;
    }
    String getLookAheadQuery(long lowerBound){
        String tableName = applicationConfig.getJdbcTableNameds2();
        String identityFieldName = applicationConfig.getIdentityFieldNameds2();
        long upperbound = applicationConfig.getCurrentCheckPoint() + applicationConfig.getBatchSize();

        String query = String.format("SELECT TOP(1) * FROM %s with (nolock) where %s > %s", tableName,
                lowerBound,upperbound);
        System.out.println(query);
        return query;
    }

    public List<Map<String, Object>> query(long lastCheckpoint) {

        long newCheckpoint = applicationConfig.getCurrentCheckPoint() + applicationConfig.getBatchSize();
        logger.info(String.format("Our New Cutoffpoint is %d", newCheckpoint));
        logger.info(String.format("Our New Cutoffpoint is %d", lastCheckpoint));
        List<Map<String, Object>> result = applicationConfig.ds2Template().queryForList(getSqlQuery(lastCheckpoint,newCheckpoint));
        logger.info(String.format("Returned %s Rows", result.size()));
        return result;
    }

    public long recordExists(long lastCheckPoint) throws Exception{
        logger.info("Looking Ahead For Records That Exist Ahead of This Checkpoint");
        String recordExistsQuery = getLookAheadQuery(lastCheckPoint);
        long nextPostTranId = 0L;
        nextPostTranId = applicationConfig.ds2Template().queryForObject(recordExistsQuery, new RowMapper<Long>() {
            @Nullable
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                Long nextPostTranId = resultSet.getLong(applicationConfig.getIdentityFieldNameds2());
                return nextPostTranId;
            }
        });
        return nextPostTranId;
    }

}
