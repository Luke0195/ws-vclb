package br.com.dscatalog.application.services.implementations;

import br.com.dscatalog.application.dtos.LogMessageDto;
import br.com.dscatalog.application.entities.LogMessage;
import br.com.dscatalog.application.mappers.LogMessageMapper;
import br.com.dscatalog.application.repositories.LogMessageRepository;
import br.com.dscatalog.application.services.usecases.LogMessageUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LogMessageUseCaseImplementation implements LogMessageUseCase {
    private final LogMessageRepository repository;

    public LogMessageUseCaseImplementation(LogMessageRepository logMessageRepository){
        this.repository = logMessageRepository;
    }

    @Override
    @Transactional
    public LogMessageDto create(LogMessageDto dto) {
       LogMessage entity = LogMessageMapper.parseDtoToEntity(dto);
       entity = repository.save(entity);
       return LogMessageMapper.parseEntityToDto(entity);
    }
    @Override
    public List<LogMessageDto> findAll() {
        List<LogMessage> entities =  repository.findAll();
        return entities.stream().map(LogMessageMapper::parseEntityToDto).toList();
    }

    @Override
    public LogMessageDto findErrorMessageById(Long id) {
        return null;
    }
}
