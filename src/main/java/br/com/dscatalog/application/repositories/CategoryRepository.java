package br.com.dscatalog.application.repositories;

import br.com.dscatalog.application.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT cat FROM Category  as cat where cat.name =:name ")
    Optional<Category> findByName(String name);
}
