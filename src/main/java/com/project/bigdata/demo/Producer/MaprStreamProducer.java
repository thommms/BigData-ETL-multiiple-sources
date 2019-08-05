package com.project.bigdata.demo.Producer;

import com.project.bigdata.demo.Serialization.JsonRowSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MaprStreamProducer {

    @Autowired
    Logger logger;

    JsonRowSerializer rowSerializer;

    KafkaProducer<String, Object> producer;

    @Autowired
    public MaprStreamProducer(JsonRowSerializer serializer, KafkaProducer<String, Object> theProducer){
        rowSerializer = serializer;
        producer = theProducer;
    }


    public void writeToKafka(ProducerRecord<String, Map> record){

    Object writableObject = rowSerializer.serialize(record.value());
        if(producer != null){
            logger.debug("Sending Record " + record.value().get(0).toString());
            producer.send(new ProducerRecord<>(record.topic(),
                    writableObject),((recordMetadata, e) -> {
                if(e != null){
                    logger.error("Could Not Send Message To Stream Server");
                    logger.error("Application Will Now Exit");
                    System.exit(1);
                }
            }));
        }
    }
}
