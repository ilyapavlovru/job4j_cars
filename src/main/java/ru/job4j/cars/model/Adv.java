package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "adv")
public class Adv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @ManyToOne
    @JoinColumn(name = "car_brand_id")
    private CarBrand carBrand;

    @ManyToOne
    @JoinColumn(name = "car_body_type_id")
    private CarBodyType carBodyType;

    private int price;

    private byte[] image;

    public Adv() {
    }

    public Adv(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Adv of(String name, CarBrand carBrand) {
        Adv adv = new Adv();
        adv.name = name;
        adv.created = new Date(System.currentTimeMillis());
        adv.carBrand = carBrand;
        return adv;
    }

    public Adv(int id, String name, String description, String status,
               CarBrand carBrand, CarBodyType carBodyType, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = new Date(System.currentTimeMillis());
        this.status = status;
        this.carBrand = carBrand;
        this.carBodyType = carBodyType;
        this.price = price;
    }

    public int getId() {
        return id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    public CarBodyType getCarBodyType() {
        return carBodyType;
    }

    public void setCarBodyType(CarBodyType carBodyType) {
        this.carBodyType = carBodyType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adv adv = (Adv) o;
        return id == adv.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Adv{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", created=" + created +
                ", carBrand=" + carBrand +
                ", carBodyType='" + carBodyType + '\'' +
                ", price=" + price +
                '}';
    }
}
