package hu.bets.common.util.schema;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;

public class SchemaValidator {

    public void validatePayload(String payload, String pathToSchema) {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(pathToSchema)) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject(payload));
        } catch (ValidationException exception) {
            StringBuilder allErrors = new StringBuilder(exception.getMessage());
            for (ValidationException exc: exception.getCausingExceptions()) {
                allErrors.append("\n").append(exc.getMessage());
            }
            throw new InvalidScemaException(allErrors.toString());
        } catch (IOException exception) {
            throw new IllegalArgumentException("Error opening schema file. ", exception);
        }
    }
}
