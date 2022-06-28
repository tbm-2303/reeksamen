package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
@NamedQuery(name = "Location.deleteAll", query = "DELETE FROM Location")
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
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST)
    private List<Match> matches = new ArrayList<>();



    public Location(String address, String city, String con, String name) {
        this.address = address;
        this.city = city;
        this.con = con;
        this.name = name;
    }

    public Location() {
    }



    //add
    public void addMatch(Match match) {
        this.matches.add(match);
        match.setLocation(this);
    }
    public void removeMatch(Match match) {
        this.matches.remove(match);
        match.setLocation(null);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Match> getMatches() { return matches; }
    public void setMatches(List<Match> matches) { this.matches = matches; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCon() { return con; }
    public void setCon(String con) { this.con = con; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
