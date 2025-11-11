package observability.app;

import observability.FrevTp3Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class MongoConnectionTest implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;

    public MongoConnectionTest(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    public static void main(String[] args) {
        SpringApplication.run(FrevTp3Application.class, args);
    }


    @Override
    public void run(String... args) {
        try {
            String dbName = mongoTemplate.getDb().getName();
            System.out.println("✅ Connected to MongoDB Atlas database: " + dbName);
        } catch (Exception e) {
            System.err.println("❌ MongoDB connection failed: " + e.getMessage());
        }
    }
}
