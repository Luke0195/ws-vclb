package br.com.dscatalog.application.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;


@Entity
@Table(name = "tb_log_message")
public class LogMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Column(name = "created_at")
    private Instant createdAt;
    private String endpoint;

    public LogMessage() {
    }

    ;

    public LogMessage(Long id, String description, Instant createdAt, String endpoint) {
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
