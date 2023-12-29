package br.com.dscatalog.application.services.usecases;

import br.com.dscatalog.application.dtos.LogMessageDto;

import java.util.List;

public interface LogMessageUseCase {

    LogMessageDto create(LogMessageDto dto);
    List<LogMessageDto> findAll();

    LogMessageDto findErrorMessageById(Long id);
}
