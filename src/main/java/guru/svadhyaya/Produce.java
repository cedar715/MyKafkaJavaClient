package guru.svadhyaya;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Produce {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        String topicName = "quote-topic";

        ProducerRecord<String, String> rec1 = new ProducerRecord<>(topicName, "Booking 133 accepted");
        ProducerRecord<String, String> rec2 = new ProducerRecord<>(topicName, "Booking 233 accepted");
        ProducerRecord<String, String> rec3 = new ProducerRecord<>(topicName, "Booking 33 accepted");

        producer.send(rec1);
        producer.send(rec2);
        producer.send(rec3);

        producer.flush();
        producer.close();

    }
}
