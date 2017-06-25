package hu.bets.common.util;

import org.apache.log4j.Logger;

public class EnvironmentVarResolver {

    private static final Logger LOGGER = Logger.getLogger(EnvironmentVarResolver.class);

    public static String getEnvVar(String name) {
        String envVar = System.getenv(name);

        if (envVar == null) {
            envVar = System.getProperty(name);
        }

        LOGGER.info("Environment variable resolution: ["+name+": "+envVar+"].");
        return envVar == null ? "" : envVar;
    }
}
