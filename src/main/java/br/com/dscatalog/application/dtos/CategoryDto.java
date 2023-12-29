package br.com.dscatalog.application.dtos;
import br.com.dscatalog.application.entities.Category;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;
public class CategoryDto implements Serializable {

    private Long id;
    @NotBlank(message="The field name must be required and cannot be empty!")
    private String name;


    public CategoryDto(){}

    public CategoryDto(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public CategoryDto(Category entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDto that = (CategoryDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
