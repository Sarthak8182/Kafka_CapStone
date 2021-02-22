package com.example.temp;
import com.example.product;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import java.util.HashMap;
import java.util.Map;

public class serdesapp extends Serdes {


    static final class serdeproduct extends WrapperSerde<product> {
        serdeproduct() {
            super(new serializer<>(), new deserializer<>());
        }
    }

    public static Serde<product> product() {
        serdeproduct serde = new serdeproduct();

        Map<String, Object> serdeConfigs = new HashMap<>();
        serdeConfigs.put(deserializer.VALUE_CLASS_NAME_CONFIG, product.class);
        serde.configure(serdeConfigs, false);

        return serde;
    }


}