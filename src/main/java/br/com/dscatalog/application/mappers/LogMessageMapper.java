package br.com.dscatalog.application.mappers;

import br.com.dscatalog.application.dtos.LogMessageDto;
import br.com.dscatalog.application.entities.LogMessage;

import java.time.Instant;

public class LogMessageMapper {

    private LogMessageMapper() {
    }

    public static LogMessageDto parseEntityToDto(LogMessage entity) {
        LogMessageDto dto = new LogMessageDto();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setEndpoint(entity.getEndpoint());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public static LogMessage parseDtoToEntity(LogMessageDto dto) {
        LogMessage entity = new LogMessage();
        entity.setDescription(dto.getDescription());
        entity.setEndpoint(dto.getEndpoint());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

    public static LogMessageDto makeLogMessage(String description, String endpoint) {
        LogMessageDto dto = new LogMessageDto();
        dto.setDescription(description);
        dto.setEndpoint(endpoint);
        dto.setCreatedAt(Instant.now());
        return dto;
    }
}
