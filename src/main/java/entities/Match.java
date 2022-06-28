package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches")
@NamedQuery(name = "Match.deleteAllRows", query = "DELETE from Match ")
public class Match  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 25)
    private int id;

    @Column(name = "opponent")
    private String opponent;

    @Column(name = "judge")
    private String judge;

    @Column(name = "type")
    private String type;

    @Column(name = "inDoor")
    private String inDoor;

    @ManyToMany(mappedBy="matches")
    private List<Player> players = new ArrayList<>();




    public Match(String opponent, String judge, String type, String inDoor) {
        this.opponent = opponent;
        this.judge = judge;
        this.type = type;
        this.inDoor = inDoor;
    }

    public Match() {
    }

    public String getOpponent() { return opponent; }
    public void setOpponent(String opponent) { this.opponent = opponent; }
    public String getJudge() { return judge; }
    public void setJudge(String judge) { this.judge = judge; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getInDoor() { return inDoor; }
    public void setInDoor(String inDoor) { this.inDoor = inDoor; }



    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }


}
