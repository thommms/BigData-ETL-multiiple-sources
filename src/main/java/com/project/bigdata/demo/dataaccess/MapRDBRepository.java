package com.project.bigdata.demo.dataaccess;

import com.project.bigdata.demo.Configuration.ApplicationConfig;
import com.project.bigdata.demo.models.KeyValueItem;
import com.project.bigdata.demo.utils.ConnectionUtils;
import com.mapr.db.Admin;
import com.mapr.db.MapRDB;
import com.mapr.db.TableDescriptor;
import com.mapr.db.exceptions.DBException;
import org.ojai.Document;
import org.ojai.DocumentStream;
import org.ojai.store.Connection;
import org.ojai.store.DocumentStore;
import org.ojai.store.QueryCondition;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import java.util.*;

@Component
@Singleton
public class MapRDBRepository {


    ApplicationConfig applicationConfig;

    Connection connection;

    DocumentStore store;

    Logger logger;



    @Autowired
    public MapRDBRepository(ApplicationConfig applicationConfig, Logger _logger){
        this.logger = _logger;
      this.applicationConfig = applicationConfig;
      this.connection = ConnectionUtils.connection();
        String path = applicationConfig.getProgressTrackingTableName();
        TableDescriptor descriptor = MapRDB.newTableDescriptor(path).setBulkLoad(false);
        try (Admin admin = MapRDB.newAdmin()) {
            if (!admin.tableExists(path)) {
                admin.createTable(descriptor).close();
            }
            store = connection.getStore(path);
        } catch (DBException exception) {
            logger.error("Error Connecting to DB");
            logger.error(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void insert(KeyValueItem[] data) {
        if (data.length < 1 || data == null)
            return;
        for (KeyValueItem item : data) {
            Document doc = connection.newDocument(item);
            String id = UUID.randomUUID().toString();
            doc.setId(id);
            store.insert(doc);
        }
        store.flush();
    }

    public void insert(KeyValueItem data) {
        if (data != null) {
            data.set_id(UUID.randomUUID().toString());
            Document document = connection.newDocument(data);
            store.insert(document);
            store.flush();
        }

    }


    protected QueryCondition buildQueryCondition(Map<String, Object> filter) {
        QueryCondition condition = connection.newCondition().and();
        for (Map.Entry<String, Object> entry :
                filter.entrySet()) {
            condition.is(entry.getKey(), QueryCondition.Op.EQUAL, entry.getValue().toString());
        }
        return condition.close().build();
    }

    public List<KeyValueItem> query(Map<String, Object> queryParams) {
        QueryCondition query = buildQueryCondition(queryParams);
        try {
            DocumentStream stream = store.find(query);
            List<KeyValueItem> t = new ArrayList<>();
            for (Document var : stream) {
                KeyValueItem classInstance = var.toJavaBean(KeyValueItem.class);
                t.add(classInstance);
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public void update(KeyValueItem data) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("key", data.getKey());
        List<KeyValueItem> res = query(entry);
        if (res == null || res.isEmpty()) {
            insert(data);
            System.out.println("Done Inserting OFFSET: " + data.getValue());
        } else {
            KeyValueItem item = res.get(0);
            item.setValue(data.getValue());
            Document document = connection.newDocument(item);
            store.insertOrReplace(document);
            store.flush();
            System.out.println("Done Updating OFFSET: " + data.getValue());
        }
    }
}
