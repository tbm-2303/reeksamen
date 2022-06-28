package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "locations")
@NamedQuery(name="Location.deleteAll", query = "DELETE FROM Location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 25)
    private int id;

    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "con", nullable = false)
    private String con;


    public Location(String address, String city, String con) {
        this.address = address;
        this.city = city;
        this.con = con;
    }

    public Location() {
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
