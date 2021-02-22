package com.example;

import com.example.temp.deserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class consumer {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(consumer.class.getName());

        String bootstrapServers = "localhost:9092";
        String groupId = "mygrp";
        String topic = "capProject";

        // create consumer prop
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer.class);
        properties.put(deserializer.VALUE_CLASS_NAME_CONFIG, product.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        KafkaConsumer<String, product> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topic));
        db d= new db();

        //data

        while (true) {
            ConsumerRecords<String, product> records =
                    consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, product> record:records) {
                d.InsRow(record.value());
                logger.info("Key: " + record.key() + ", Value: " + record.value());
                logger.info("Partition: " + record.partition() + ", Offset:" + record.offset());
            }
        }
    }
}