package br.com.dscatalog.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;


public class LogMessageDto implements Serializable {

    private Long id;
    private String description;
    @JsonProperty("created_at")
    private Instant createdAt;
    private String  endpoint;
    public LogMessageDto(){}

    public LogMessageDto(Long id, String description, Instant createdAt, String endpoint){
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
        this.endpoint = endpoint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", endpoint='" + endpoint + '\'' +
                '}';
    }
}
