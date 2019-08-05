package com.interswitch.bigdata.demo.dataaccess;

import com.interswitch.bigdata.demo.Configuration.ApplicationConfig;
import com.interswitch.bigdata.demo.utils.ConnectionUtils;
import com.mapr.db.Admin;
import com.mapr.db.MapRDB;
import com.mapr.db.TableDescriptor;
import com.mapr.db.exceptions.DBException;
import org.json.JSONObject;
import org.ojai.Document;
import org.ojai.store.Connection;
import org.ojai.store.DocumentStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import java.io.Serializable;

@Component
@Singleton
public class PersistentRepository implements Serializable {

    ApplicationConfig applicationConfig;
    Connection connection;
    DocumentStore store;

    @Autowired
    public PersistentRepository(ApplicationConfig applicationConfig){

        this.applicationConfig = applicationConfig;
        this.connection = ConnectionUtils.connection();
        String path = applicationConfig.getDbStoragePathds1();
        TableDescriptor descriptor = MapRDB.newTableDescriptor(path).setBulkLoad(false);
        try (Admin admin = MapRDB.newAdmin()) {
            if (!admin.tableExists(path)) {
                admin.createTable(descriptor).close();
            }
            store = connection.getStore(path);
        } catch (DBException exception) {
            System.out.println("Error Connecting to DB");
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void insert(String[] data) {
        if (data.length < 1 || data == null)
            return;

        for (String item : data) {
            Document doc = connection.newDocument(item);
            String id = doc.getString(applicationConfig.getIdentityFieldNameds1());
            doc.setId(id);
            store.insert(doc);
        }
        store.flush();
    }

    public void insert(String data) {
        if (data != null  && !data.isEmpty()) {
            Document document = connection.newDocument(data);
            JSONObject object = new JSONObject(data);
            String id_1 = object.get(applicationConfig.getTableIdentityFieldNameds1()).toString();
            String id_2 = object.get(applicationConfig.getIdentityFieldNameds1()).toString();
            document.setId(String.format("%s|%s", id_1, id_2));
            store.insertOrReplace(document);
            store.flush();
        }

    }

}
