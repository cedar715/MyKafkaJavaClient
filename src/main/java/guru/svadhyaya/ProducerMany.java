package guru.svadhyaya;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerMany {
    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.serializer", IntegerSerializer.class.getName());
        props.setProperty("value.serializer", StringSerializer.class.getName());

        // try 1 : no batching
//         props.setProperty("batch.size", "0");

        // try 2:   Enable batching and tune it to produce ~ 10 messages/batch
        props.setProperty("linger.ms", "1000");
        props.setProperty("compression.type", "gzip");

        KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);

        //produce 5000 msgs with random keys
        for (int i = 0; i < 5000; i++) {
            int key = ThreadLocalRandom.current().nextInt(1, 1500);
            ProducerRecord<Integer, String> rec = new ProducerRecord<>("quote4-topic", key, "Booking accepted!");
            producer.send(rec);
            Thread.sleep(10);
        }

        producer.flush();
        producer.close();

    }

}
