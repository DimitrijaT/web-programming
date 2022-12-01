package mk.ukim.finki.wpaud.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Product {

    @Id
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;

//    @Transient
    @ManyToOne
    private Category category;

    @ManyToOne
    private Manufacturer manufacturer;

    // Long id, kje bide avtomatski generirano.
    public Product(String name, Double price, Integer quantity, Category category, Manufacturer manufacturer) {
        this.id = (long) (Math.random() * 1000);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.manufacturer = manufacturer;
    }

    public Product() {

    }
}

