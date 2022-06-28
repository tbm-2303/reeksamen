package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
@NamedQuery(name = "Location.deleteAllRows", query = "DELETE from Location ")
public class Location implements Serializable {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "condition")
    private String condition;


    @OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST)
    private List<Match> matches = new ArrayList<>();



    public Location(String address, String city, String condition) {
        this.address = address;
        this.city = city;
        this.condition = condition;
    }

    public Location() {
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
}
