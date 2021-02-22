package com.example;

import com.example.temp.serializer;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.io.*;
import java.util.List;
import java.util.Properties;

public class producer {
    public static void main(String[] args) {

        String bootstrapServers = "127.0.0.1:9092";
        String topic = "capProject";

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializer.class.getName());

        KafkaProducer<String, product> producer = new KafkaProducer<String, product>(properties);
        product p1 = new product();
        try {
            //reading from json
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputstream = new FileInputStream(new File("product.json"));
            TypeReference<List<product>> typeReference = new TypeReference<List<product>>() { };
            List<product> prod= mapper.readValue(inputstream,typeReference);


     /*p1.withPogId("1");
       p1.withBrand("Apple");
       p1.withCategory("C2");
       p1.withCountry("California");
       p1.withDescription("Electronics");
       p1.withCreationtime("Tuesday");
       p1.withPrice("84999");
       p1.withQuantity("5");
       p1.withSize("N/A");
       p1.withStock("4");
       p1.withSubcategory("Iphone");
       p1.withSupc("AA");
       p1.withSellercode("abcd");
       */

            //sending json data as list of product object form using for loop
            for(product p : prod) {
                ProducerRecord<String, product> record =
                        new ProducerRecord<String, product>(topic, p.getPogId(), p);

                producer.send(record);
                producer.flush();
                producer.close();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}