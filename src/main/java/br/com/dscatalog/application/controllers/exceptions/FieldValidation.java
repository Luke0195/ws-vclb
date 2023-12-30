package br.com.dscatalog.application.controllers.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class FieldValidation implements Serializable {

    @JsonProperty("field_name")
    private String fieldName;
    @JsonProperty("field_description")
    private String fieldDescription;

    public FieldValidation() {
    }

    public FieldValidation(String fieldName, String fieldDescription) {
        this.fieldName = fieldName;
        this.fieldDescription = fieldDescription;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldValidation that = (FieldValidation) o;
        return Objects.equals(fieldName, that.fieldName) && Objects.equals(fieldDescription, that.fieldDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, fieldDescription);
    }

    @Override
    public String toString() {
        return "FieldValidation{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldDescription='" + fieldDescription + '\'' +
                '}';
    }
}
