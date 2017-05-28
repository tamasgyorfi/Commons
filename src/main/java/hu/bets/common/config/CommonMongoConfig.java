package hu.bets.common.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import hu.bets.common.util.EnvironmentVarResolver;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonMongoConfig {

    private static final String DB_URI_KEY = "MONGODB_URI";

    @Bean
    public static MongoCollection<Document> getMongoClient(String dbName, String collectionName) {
        String dbUri = EnvironmentVarResolver.getEnvVar(DB_URI_KEY);

        MongoClientURI clientURI = new MongoClientURI(dbUri);
        MongoClient client = new MongoClient(clientURI);

        MongoDatabase database = client.getDatabase(dbName);
        return database.getCollection(collectionName);
    }

}
