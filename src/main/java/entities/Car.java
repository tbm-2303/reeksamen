package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
@NamedQuery(name = "Car.deleteAllRows", query = "DELETE from Car ")

public class Car implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 25)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "make")
    private String make;

    @Column(name = "year")
    private String year;

    @Column(name = "sponsor")
    private String sponsor;

    @Column(name = "color")
    private String color;

    @ManyToMany(mappedBy="cars")
    private List<Race> races = new ArrayList<>();

    @OneToMany(mappedBy = "car", cascade = CascadeType.PERSIST)
    private List<Driver> drivers = new ArrayList<>();


    public Car(String name, String brand, String make, String year, String sponsor, String color) {
        this.name = name;
        this.brand = brand;
        this.make = make;
        this.year = year;
        this.sponsor = sponsor;
        this.color = color;
    }


    public Car() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
    public String getSponsor() { return sponsor; }
    public void setSponsor(String sponsor) { this.sponsor = sponsor; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public List<Race> getRaces() { return races; }
    public void setRaces(List<Race> races) { this.races = races; }
    public List<Driver> getDrivers() { return drivers; }
    public void setDrivers(List<Driver> drivers) { this.drivers = drivers; }


    public void addRace(Race race) {
        this.races.add(race);
    }
    public void addDriver(Driver driver) {
        this.drivers.add(driver);
        driver.setCar(this);
    }
    public void removeDriver(Driver driver) {
        this.drivers.remove(driver);
        driver.setCar(null);
    }
}
