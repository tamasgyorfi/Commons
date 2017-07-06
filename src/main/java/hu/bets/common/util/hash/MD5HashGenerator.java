package hu.bets.common.util.hash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5HashGenerator implements HashGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MD5HashGenerator.class);

    public String getHash(Object object) {
        try {
            byte[] bytes = getBytes(object);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(bytes);
            byte[] digest = md5.digest();

            return new BigInteger(1, digest).toString(16);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.info("No MD5 algorithm found.");
        }

        // This should never happen
        return "Error hashing bets.";
    }

    private byte[] getBytes(Object object) {
        return new byte[0]; // Should be fixed later. will always return the same hash otherwise
    }
}
