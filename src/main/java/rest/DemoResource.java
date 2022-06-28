package rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dtos.UserDTO;
import entities.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.*;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import facades.UserFacade;
import utils.EMF_Creator;
import utils.HttpUtils;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF_Creator.createEntityManagerFactory());


    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("setup")
    public String setup(){
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        User user = new User("timmy", "timmy123");
        User admin = new User("james", "james123");
        User both = new User("kent", "kent123");
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        Location location = new Location("Per Henrik Lings Allé 2","2100 København","good", "Parken");
        Location location2 = new Location("aliancetrase 2","Berlin", "good","Aliance stadion");
        Match match = new Match("oppnent","judge","type","indoor");
        Match match2 = new Match("oppnent2","judge2","type2","indoor2");
        Player player = new Player("name","phone","email","status");

        //relation
        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);
        location.addMatch(match);
        location2.addMatch(match2);
        player.addUser(user);

        //persisting
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);
        em.persist(location);
        em.persist(location2);
        em.persist(match);
        em.persist(match2);
        em.persist(player);
        em.getTransaction().commit();
        em.close();
        return "{\"msg\":\"setup all good\"}";
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }









    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("users")
    //@RolesAllowed("user")
    public String getAllUsers() {
        List<UserDTO> userDTOList = FACADE.getAllUsers();
        return GSON.toJson(userDTOList);
    }

}