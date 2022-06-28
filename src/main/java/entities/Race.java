package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "races")
@NamedQuery(name = "Race.deleteAllRows", query = "DELETE from Race ")
public class Race implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    // TODO change to Date class.
    @Column(name = "date")
    private Timestamp date;

    @Column(name = "duration")
    private int duration;


    @JoinTable(
            name = "race_car",
            joinColumns = @JoinColumn(name = "race_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id"))
    @ManyToMany
    private List<Car> cars = new ArrayList<>();

    public Race(String name, String location, Timestamp date, int duration) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.duration = duration;
    }

    public Race() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public List<Car> getCarList() { return cars; }
    public void setCarList(List<Car> carList) { this.cars = carList; }



    public void addCar(Car car) {
        this.cars.add(car);
        if(!car.getRaces().contains(this)){
            car.addRace(this);
        }
    }
    public void removeCar(Car car) {
        this.cars.remove(car);
        //if(!car.getRaceList().contains(this))
        car.getRaces().remove(this);
    }
}