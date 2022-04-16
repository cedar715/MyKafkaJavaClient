package guru.svadhyaya;

import java.util.concurrent.ExecutionException;

public class TestKafkaAdmin {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        KafkaAdmin.deleteTopic("quote-feedback");
        KafkaAdmin.createTopicWithConfig("quote-feedback", 2, (short) 3);
//        KafkaAdmin.describeTopic("quote-feedback");
    }
}
