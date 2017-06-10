package hu.bets.common.util;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5HashGenerator implements HashGenerator {

    private static final Logger LOGGER = Logger.getLogger(MD5HashGenerator.class);

    public String getHash(Object object) {
        try {
            String betsString = object.toString();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(betsString.getBytes());
            byte[] digest = md5.digest();

            return new BigInteger(1, digest).toString(16);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.info("No MD5 algorithm found.");
        }

        // This should never happen
        return "Error hashing bets.";
    }

}
