package dtos;

import entities.Location;
import entities.Match;
import entities.Player;

import java.util.ArrayList;
import java.util.List;

public class MatchDTO {
    private int id;
    private String opponent;
    private String judge;
    private String type;
    private String inDoor;
    private LocationDTO locationDTO;
    private List<Player> playerList;

    public MatchDTO() {

    }

    public MatchDTO(Match match) {
        if (match.getId() != 0)
            this.id = match.getId();
        this.opponent = match.getOpponent();
        this.judge = match.getJudge();
        this.type = match.getType();
        this.inDoor = match.getInDoor();
        this.locationDTO = new LocationDTO(match.getLocation());
        if (match.getPlayers() != null) {
            this.playerList = match.getPlayers();
        }
    }



    public static List<MatchDTO> getDTOS(List<Match> matches){
        List<MatchDTO> matchDTOS = new ArrayList<>();
        if(matches != null){
            matches.forEach(match -> matchDTOS.add(new MatchDTO(match)));
        }
        return matchDTOS;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getOpponent() { return opponent; }
    public void setOpponent(String opponent) { this.opponent = opponent; }
    public String getJudge() { return judge; }
    public void setJudge(String judge) { this.judge = judge; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getInDoor() { return inDoor; }
    public void setInDoor(String inDoor) { this.inDoor = inDoor; }
    public LocationDTO getLocationDTO() { return locationDTO; }
    public void setLocation(LocationDTO locationDTO) { this.locationDTO = locationDTO; }
    public List<Player> getPlayerList() { return playerList; }
    public void setPlayerList(List<Player> playerList) { this.playerList = playerList; }
}