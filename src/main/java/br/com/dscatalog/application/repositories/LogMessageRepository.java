package br.com.dscatalog.application.repositories;

import br.com.dscatalog.application.entities.LogMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogMessageRepository extends JpaRepository<LogMessage, Long> {
}
