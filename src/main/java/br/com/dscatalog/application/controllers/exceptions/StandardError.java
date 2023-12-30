package br.com.dscatalog.application.controllers.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StandardError implements Serializable {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;
    private String message;
    @JsonProperty("error_fields")
    private List<FieldValidation> fields = new ArrayList<>();

    public StandardError() {
    }

    public StandardError(Instant timestamp, Integer status, String error, String path, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldValidation> getFields() {
        return fields;
    }

    public void setFields(List<FieldValidation> fields) {
        this.fields = fields;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardError that = (StandardError) o;
        return Objects.equals(timestamp, that.timestamp) && Objects.equals(status, that.status) && Objects.equals(error, that.error) && Objects.equals(path, that.path) && Objects.equals(message, that.message) && Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, status, error, path, message, fields);
    }

    @Override
    public String toString() {
        return "StandardError{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", path='" + path + '\'' +
                ", message='" + message + '\'' +
                ", fields=" + fields +
                '}';
    }
}
