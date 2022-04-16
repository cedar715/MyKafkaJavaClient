package guru.svadhyaya;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class KafkaAdmin {
    private static final Admin admin;

    static {
        Properties brokerProps = new Properties();
        brokerProps.setProperty("bootstrap.servers", "localhost:9092");
        admin = Admin.create(brokerProps);
    }

    public static void list() throws ExecutionException, InterruptedException {
        Set<String> topics = admin.listTopics().names().get();
        topics.forEach(System.out::println);
    }

    public static void createTopic(String topicName, int partitions, short replicas) throws ExecutionException, InterruptedException {
        NewTopic topic = new NewTopic(topicName, partitions, replicas);
        admin.createTopics(List.of(topic)).all().get();
    }

    public static void createTopicWithConfig(String topicName, int partitions, short replicas) throws ExecutionException, InterruptedException {
        Map<String, String> config = Map.of("min.insync.replicas","2");
        NewTopic topic = new NewTopic(topicName, partitions, replicas).configs(config);
        admin.createTopics(List.of(topic)).all().get();
    }

    public static void deleteTopic(String topicName) throws ExecutionException, InterruptedException {
        admin.deleteTopics(List.of(topicName)).all().get();
    }

    public static void describeTopic(String topicName) throws ExecutionException, InterruptedException {
        Map<String, TopicDescription> topicDescriptionMap = admin.describeTopics(List.of(topicName)).all().get();
        topicDescriptionMap.forEach(
                (name, desc) -> {
                    System.out.printf("\nTopic = %s\n", name);
                    desc.partitions().forEach(
                            p -> {
                                System.out.printf("Partition = %d%n", p.partition());
                                System.out.printf("  Leader: = %s%n", p.leader());
                                System.out.printf("  Replicas: %n");
                                p.replicas().forEach(
                                        r -> System.out.printf("    - %s%n", r)
                                );

                            }
                    );

                }
        );
    }


    // TODO : close admin
}
