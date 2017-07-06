package hu.bets.common.util;

import org.glassfish.jersey.internal.util.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentVarResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentVarResolver.class);
    private static final String EMPTY = "";

    public static String getEnvVar(String name) {
        String envVar = System.getenv(name);

        if (envVar == null) {
            envVar = System.getProperty(name);
        }

        LOGGER.info("Environment variable resolution: [" + name + ": " + envVar + "].");
        return envVar == null ? EMPTY : envVar;
    }

    public static String getEnvVar(String name, Producer<String> defaultValueProducer) {
        String variable = getEnvVar(name);

        return EMPTY.equals(variable) ? defaultValueProducer.call() : variable;
    }
}
