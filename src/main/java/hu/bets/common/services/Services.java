package hu.bets.common.services;

/**
 * Names of the services deployed.
 * This enum is useful for registration and lookup of the services.
 */
public enum Services {
    BETS("football-bets"),
    MATCHES("football-matches"),
    SCORES("football-scores"),
    API_GATEWAY("football-api-gateway");

    private String serviceName;

    Services(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
