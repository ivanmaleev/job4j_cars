package ru.job4j.model;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private CarBrand carBrand;
    @Enumerated(EnumType.STRING)
    private BodyType bodytype;
    private String photoPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CarBrand getBrand() {
        return carBrand;
    }

    public void setBrand(CarBrand brand) {
        this.carBrand = brand;
    }

    public BodyType getBodytype() {
        return bodytype;
    }

    public void setBodytype(BodyType type) {
        this.bodytype = type;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
