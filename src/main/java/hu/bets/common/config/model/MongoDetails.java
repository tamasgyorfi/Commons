package hu.bets.common.config.model;

public class MongoDetails {

    private String dbName;
    private String collectionName;

    public MongoDetails(String dbName, String collectionName) {
        this.dbName = dbName;
        this.collectionName = collectionName;
    }

    public String getDbName() {
        return dbName;
    }

    public String getCollectionName() {
        return collectionName;
    }
}
