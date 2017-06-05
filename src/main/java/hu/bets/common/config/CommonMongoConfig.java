package hu.bets.common.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import hu.bets.common.util.EnvironmentVarResolver;
import hu.bets.common.config.model.MongoDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonMongoConfig {

    private static final String DB_URI_KEY = "MONGODB_URI";

    @Bean
    public MongoDatabase getMongoClient(@Qualifier("mongoDBName") String mongoDB) {
        String dbUri = EnvironmentVarResolver.getEnvVar(DB_URI_KEY);

        MongoClientURI clientURI = new MongoClientURI(dbUri);
        MongoClient client = new MongoClient(clientURI);

        return client.getDatabase(mongoDB);
    }
}
