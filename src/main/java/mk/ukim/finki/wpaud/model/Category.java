package mk.ukim.finki.wpaud.model;


import lombok.Data;

@Data
public class Category {

    private String name;

    private String Description;

    public Category(String name, String surname) {
        this.name = name;
        this.Description = surname;
    }
}
