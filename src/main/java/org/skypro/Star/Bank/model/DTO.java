package org.skypro.Star.Bank.model;

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
}