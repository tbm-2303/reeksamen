package dtos;

import entities.Car;
import entities.Race;
import entities.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RaceDTO {
    private int id;
    private String name;
    private String location;
    private Timestamp date;
    private int duration;
    private List<CarDTO> carDTOs = new ArrayList<>();

    public RaceDTO(Race race) {
        if (race.getId() != 0)
            this.id = race.getId();
        this.name = race.getName();
        this.location = race.getLocation();
        this.date = race.getDate();
        this.duration = race.getDuration();
        for (Car car : race.getCarList()) {
            this.carDTOs.add(new CarDTO(car));
        }
    }
//change


    public static List<RaceDTO> getDtos(List<Race> r){
        List<RaceDTO> raceDTOSdtos = new ArrayList();
        r.forEach(um -> raceDTOSdtos.add(new RaceDTO(um)));
        return raceDTOSdtos;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Timestamp getDate() { return date; }
    public void setDate(Timestamp  date) { this.date = date; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public List<CarDTO> getCarDTOs() { return carDTOs; }
    public void setCarDTOs(List<CarDTO> carDTOs) { this.carDTOs = carDTOs; }
}