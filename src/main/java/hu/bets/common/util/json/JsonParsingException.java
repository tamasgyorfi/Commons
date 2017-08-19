package hu.bets.common.util.json;

public class JsonParsingException extends RuntimeException{

    public JsonParsingException(Exception e) {
        super(e);
    }
}
