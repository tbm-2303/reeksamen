package dtos;

import entities.Match;
import entities.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerDTO {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String status;
    private List<Match> matchList;

    public PlayerDTO(){

    }

    public PlayerDTO(Player player){
        if (player.getId() != 0 ){
            this.id = player.getId();
        }
        this.name = player.getName();
        this.phone = player.getPhone();
        this.email = player.getEmail();
        this.status = player.getStatus();
        if (player.getMatches() != null) {
            this.matchList = player.getMatches();
        }
    }


    public static List<PlayerDTO> getDTOS(List<Player> players){
        List<PlayerDTO> playerDTOs = new ArrayList<>();
        if(players != null){
            players.forEach(player -> playerDTOs.add(new PlayerDTO(player)));
        }
        return playerDTOs;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Match> getMatchList() { return matchList; }
    public void setMatchList(List<Match> matchList) { this.matchList = matchList; }
}
