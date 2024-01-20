package br.com.dscatalog.application.dtos;

import br.com.dscatalog.application.entities.Category;
import br.com.dscatalog.application.entities.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;

public class ProductDto implements Serializable {

    private Long id;
    @NotBlank(message = "The field name must be required")
    private String name;
    @NotBlank(message = "The field description must be required")
    private String description;
    @JsonProperty("img_url")
    @NotBlank(message = "The field img_url must be required")
    private String imgUrl;

    private Instant date;

    private BigDecimal price;
    @JsonProperty("created_at")
    private Instant createdAt;
    @JsonProperty("updated_at")
    private Instant updateAt;
    private final List<Category> categories = new ArrayList<>();

    public ProductDto() {
    }

    public ProductDto(Long id, String name, String description, String imgUrl, Instant date, BigDecimal price, Instant createdAt, Instant updateAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.date = date;
        this.price = price;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.imgUrl = product.getImgUrl();
        this.date = product.getDate();
        this.createdAt = product.getCreatedAt();
        this.updateAt = product.getUpdatedAt();
    }

    public ProductDto(Product entity, Set<Category> categories) {
        this(entity);
        for (Category cat : categories) {
            this.categories.add(new Category(cat.getId(), cat.getName()));
        }
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
