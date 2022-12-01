package mk.ukim.finki.wpaud.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(length = 4000)
    private String Description;

    public Category(String name, String surname) {
        this.id = (long) (Math.random() * 1000);
        this.name = name;
        this.Description = surname;
    }
}


