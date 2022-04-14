package guru.svadhyaya;

import org.apache.kafka.clients.admin.Admin;

import java.util.Map;
import java.util.Set;

public class AdminExCreateTopic {

    public static void main(String[] args) throws Exception {
        Admin admin = Admin.create(
                Map.of("bootstrap.servers", "192.168.0.111:9092")
        );

        printAllTopics(admin);
    }

    static void printAllTopics(Admin client) throws Exception {
        Set<String> topics = client.listTopics().names().get();
        System.out.println("Topics in the cluster: \n");
        topics.forEach(System.out::println);
    }
}
