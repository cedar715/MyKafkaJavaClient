package guru.svadhyaya;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerWithKey {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.serializer", IntegerSerializer.class.getName());
        props.setProperty("value.serializer", StringSerializer.class.getName());

        KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);

        ProducerRecord<Integer, String> r1 = new ProducerRecord<>("quote-feedback", 1000, "msg 1");
        ProducerRecord<Integer, String> r2 = new ProducerRecord<>("quote-feedback", 1001, "msg 2");
        ProducerRecord<Integer, String> r3 = new ProducerRecord<>("quote-feedback", 1000, "msg 3");

        RecordMetadata r1MetaData = producer.send(r1).get();
        System.out.printf("Key with value %d got assigned to Partition: %d and assigned offset = %d\n", r1.key(), r1MetaData.partition(), r1MetaData.offset());

        RecordMetadata r2MetaData = producer.send(r2).get();
        System.out.printf("Key with value %d got assigned to Partition: %d and assigned offset = %d\n", r2.key(), r2MetaData.partition(), r2MetaData.offset());

        RecordMetadata r3MetaData = producer.send(r3).get();
        System.out.printf("Key with value %d got assigned to Partition: %d and assigned offset = %d\n", r3.key(), r3MetaData.partition(), r3MetaData.offset());

    }
}
