package hu.bets.common.util;

public class EnvironmentVarResolver {

    public static String getEnvVar(String name) {
        String envVar = System.getenv(name);

        if (envVar == null) {
            envVar = System.getProperty(name);
        }

        return envVar == null ? "" : envVar;
    }
}
