package hu.bets.common.util;

import java.util.UUID;

public class UuidIdGenerator implements IdGenerator {

    @Override
    public String generateBetId(String prefix) {
        return prefix + ":" + UUID.randomUUID();
    }

}
