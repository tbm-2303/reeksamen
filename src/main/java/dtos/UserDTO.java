package dtos;

import entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private int id;
    private String userName;
    private String firstName;
    private String userPass;

    public UserDTO(User user) {
        if (user.getId() != 0)
            this.id = user.getId();
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
    }

    public static List<UserDTO> getDtos(List<User> u){
        List<UserDTO> userDTOSdtos = new ArrayList();
        u.forEach(um -> userDTOSdtos.add(new UserDTO(um)));
        return userDTOSdtos;
    }

    public User getEntity() {
        User u = new User(this.userName, null, this.firstName);

        return u;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getUserPass() { return userPass; }
    public void setUserPass(String userPass) { this.userPass = userPass; }
}