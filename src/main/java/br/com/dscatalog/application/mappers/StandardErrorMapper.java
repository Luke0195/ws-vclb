package br.com.dscatalog.application.mappers;

import br.com.dscatalog.application.controllers.exceptions.FieldValidation;
import br.com.dscatalog.application.controllers.exceptions.StandardError;

import java.time.Instant;
import java.util.List;

public class StandardErrorMapper {
    private StandardErrorMapper() {
    }

    public static StandardError makeStandardError(
            Integer status, String error, String path,
            String message, List<FieldValidation> list) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(status);
        standardError.setError(error);
        standardError.setPath(path);
        standardError.setMessage(message);
        standardError.setFields(list);
        return standardError;
    }
}
