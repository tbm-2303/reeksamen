package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "players")
@NamedQuery(name = "Player.deleteAllRows", query = "DELETE from Player ")
public class Player implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    @JoinTable(
            name = "player_match",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "match_id"))
    @ManyToMany
    private List<Match> matches = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Player(String name, String phone, String email, String status) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public Player() {
    }


    public void addUser(User user){
        setUser(user);
        user.setPlayer(this);
    }
    //add relation
    public void addMatch(Match match) {
        matches.add(match);
        match.addPlayer(this);

    }
//remove
    public void removeMatch(Match match){
        this.matches.remove(match);
        match.getPlayers().remove(this);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public List<Match> getMatches() { return matches; }
    public void setMatches(List<Match> matches) { this.matches = matches; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
