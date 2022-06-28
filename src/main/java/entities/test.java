package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "location")
@NamedQuery(name="Location.deleteAll", query = "DELETE FROM test ")
public class test implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 25)
    private int id;

    @Column(name = "address", nullable = false)
    private String address;


    public test(String address) {
        this.address = address;
    }

    public test() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
