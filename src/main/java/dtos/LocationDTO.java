package dtos;

import entities.Location;
import entities.Match;

import java.util.ArrayList;
import java.util.List;

public class LocationDTO {
    private int id;
    private String address;
    private String city;
    private String con;
    private String name;
    private List<Match> matches;

    public LocationDTO(){

    }

    public LocationDTO(Location location){
      if (location.getId() != 0){
          this.id = location.getId();
      }
        this.address = location.getAddress();
        this.city = location.getCity();
        this.con = location.getCon();
        this.name = location.getName();
        if(location.getMatches() != null){
            this.matches = location.getMatches();
        }
    }

    public static List<LocationDTO> getDTOS(List<Location> locations){
        List<LocationDTO> locationDTOS = new ArrayList<>();
        if(locations != null){
            locations.forEach(location -> locationDTOS.add(new LocationDTO(location)));
        }
        return locationDTOS;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCon() { return con; }
    public void setCon(String con) { this.con = con; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Match> getMatches() { return matches; }
    public void setMatches(List<Match> matches) { this.matches = matches; }
}
