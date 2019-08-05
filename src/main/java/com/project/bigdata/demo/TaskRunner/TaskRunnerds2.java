package com.project.bigdata.demo.TaskRunner;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.project.bigdata.demo.Configuration.ApplicationConfig;
import com.project.bigdata.demo.Producer.MaprStreamProducer;
import com.project.bigdata.demo.Serialization.RowSerializer;
import com.project.bigdata.demo.dataaccess.MapRDBRepository;
import com.project.bigdata.demo.models.KeyValueItem;
import com.project.bigdata.demo.utils.RowComparator;
import com.project.bigdata.demo.utils.Utilitiesds2;
import com.project.bigdata.demo.dataaccess.PersistentRepositoryds3;
import com.project.bigdata.demo.dataaccess.SQLServerDataProviderds2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class TaskRunnerds2 extends AbstractScheduledService implements Serializable {

    private long checkPoint;
    private int runIterations;

    @Autowired
    ApplicationConfig applicationConfig;

    @Autowired
    MapRDBRepository kevalueRepository;

    @Autowired
    SQLServerDataProviderds2 dataProvider;


    @Autowired
    Logger logger;

    @Autowired
    Utilitiesds2 utilities;

    @Autowired
    MaprStreamProducer streamProducer;

    @Autowired
    RowSerializer rowSerializer;

    @Autowired
    PersistentRepositoryds3 persistentRepository;

    @Override
    protected void runOneIteration() throws Exception {
//        checkPoint= applicationConfig.getCurrentCheckPoint();
        logger.info(String.format("Runing Iteration %s ", runIterations));
        List<Map<String,Object>> result = null;
        try{
            result = dataProvider.query(checkPoint);
        }
        catch (Exception exception){
            logger.error(exception.getMessage());
            exception.printStackTrace();
            updateRecord(checkPoint);
        }

        if(result == null || result.size() < 1) {
            System.out.println("Result Set Is Null");
            try {
                long lookAhead = dataProvider.recordExists(checkPoint);
                if (lookAhead > 0) {
                    updateRecord(lookAhead);
                    checkPoint = lookAhead;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error(ex.getMessage());
            }
        }
        else{

            if (applicationConfig.isPushToStream()) {
                System.out.println("Pusing To MapR Streams");
                result.parallelStream().forEach((t) -> {
                    streamProducer.writeToKafka(new ProducerRecord<>(applicationConfig.getStreamingTargetTopicds2(), t));
                });
            }
            if (applicationConfig.isSaveToDB()) {
                System.out.println("===============================Pushing to MapRDB===============================================");
                result.parallelStream().forEach((t) -> {
                    Object record = rowSerializer.serialize(t);
                    logger.info("Sending Record To DB");
                    persistentRepository.insert(record.toString());
                    logger.info("Finished Sending The Record To MapRDB");
                });
            }
            Map<String, Object> lastResult = Collections.max(result, new RowComparator(applicationConfig.getIdentityFieldNameds2()));
            long lastId = (long) lastResult.get(applicationConfig.getIdentityFieldNameds2());
            System.out.println("===============at checkpoint : " + checkPoint + " for ds2 ===================");

            updateRecord(lastId);
            checkPoint = lastId;

        }
    }

    void updateRecord(long recordValue){
        Map<String, Object> searchCriteria = new HashMap<>();
        searchCriteria.put("key", utilities.buildKeyScheme());
        List<KeyValueItem> items = kevalueRepository.query(searchCriteria);
        if(items != null && items.size() > 0) {
            KeyValueItem item = items.get(0);
            item.setValue(String.valueOf(recordValue));
            kevalueRepository.update(item);
        }
        else {
            KeyValueItem  item = new KeyValueItem();
            item.setKey(utilities.buildKeyScheme());
            item.setValue(item.getValue());
            kevalueRepository.update(item);
        }
    }

    @Override
    protected AbstractScheduledService.Scheduler scheduler() {
        Integer interval = applicationConfig.getTimeIntervalInSeconds();
        return AbstractScheduledService.Scheduler.newFixedDelaySchedule(0L, interval, TimeUnit.SECONDS);
    }

    @Override
    protected void shutDown() throws Exception {

    }

    @Override
    protected void startUp() throws Exception {
        runIterations = 0;
        System.out.println(String.format("Starting At %s", new Date()));
        System.out.println(String.format("Waiting For %s before running first iteration", applicationConfig.getTimeIntervalInSeconds()));
        Map<String, Object> searchCriteria = new HashMap<>();
        searchCriteria.put("key", utilities.buildKeyScheme());
        List<KeyValueItem> items = kevalueRepository.query(searchCriteria);
        if(items != null && items.size() > 0) {
            System.out.printf("===================value for ds2=>%s =====================\n",items.get(0).getValue());
            checkPoint = Long.parseLong(items.get(0).getValue());
            System.out.println("We Retrieved The Checkpoint with value "+checkPoint);
        }
        else {
            checkPoint = applicationConfig.getCurrentCheckPoint();
            System.out.println("We Bound The Checkpoint with:  "+checkPoint);
        }
    }
}
