package br.com.dscatalog.application.services.implementations;

import br.com.dscatalog.application.dtos.LogMessageDto;
import br.com.dscatalog.application.entities.LogMessage;
import br.com.dscatalog.application.mappers.LogMessageMapper;
import br.com.dscatalog.application.repositories.LogMessageRepository;
import br.com.dscatalog.application.services.exceptions.ResourceNotExists;
import br.com.dscatalog.application.services.usecases.LogMessageUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LogMessageUseCaseImplementation implements LogMessageUseCase {
    @Autowired
    private LogMessageRepository repository;


    @Override
    @Transactional
    public LogMessageDto create(LogMessageDto dto) {
        LogMessage entity = LogMessageMapper.parseDtoToEntity(dto);
        entity = repository.save(entity);
        return LogMessageMapper.parseEntityToDto(entity);
    }

    @Override
    public List<LogMessageDto> findAll() {
        List<LogMessage> entities = repository.findAll();
        return entities.stream().map(LogMessageMapper::parseEntityToDto).toList();
    }

    @Override
    public LogMessageDto findErrorMessageById(Long id) {
        Optional<LogMessage> logAlreadyExists = repository.findById(id);
        LogMessage entity = logAlreadyExists.orElseThrow(() -> new ResourceNotExists("This logmessage doesn't not exists "));
        return LogMessageMapper.parseEntityToDto(entity);
    }
}
