package org.skypro.Star.Bank.model;

import java.util.Objects;
import java.util.UUID;

public class DTO {
    private String id;
    private String name;
    private String text;

    public DTO(String name, String id, String text) {
        this.name = name;
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DTO dto = (DTO) o;
        return Objects.equals(id, dto.id) && Objects.equals(name, dto.name) && Objects.equals(text, dto.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, text);
    }

    @Override
    public String toString() {
        return "DTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

}