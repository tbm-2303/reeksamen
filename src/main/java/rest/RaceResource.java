package rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Driver;
import entities.Race;
import facades.RaceFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("race")
public class RaceResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final RaceFacade FACADE = RaceFacade.getRaceFacade(EMF_Creator.createEntityManagerFactory());
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    @GET
    @Path("populate")
    @Produces({MediaType.APPLICATION_JSON})
    public String populatel() {
        EntityManager em = EMF.createEntityManager();
        //create some races
        em.getTransaction().begin();
        //make relations
        //persist ->  em.persist(?);
        em.getTransaction().commit();
        return "{\"msg\":\"setup all good\"}";
    }




    //us 1 users can see available races.
    @GET
    @Path("getall")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<Race> list = FACADE.getAll();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        List<RaceDTO> raceDTOS = new ArrayList<>();

        for (Race race : list) {
            if (race.getDate().after((Date) ts)){
                raceDTOS.add(new RaceDTO(race));
            }
        }
        return Response
                .ok()
                .entity(GSON.toJson(raceDTOS))
                .build();
    }

    //us 3 get races associated with a driver.
    @GET
    @Path("driver/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRacesByDriverUsername(@PathParam("username") String username) throws  errorhandling.EntityNotFoundException {
        List<Race> list = FACADE.getRacesAssociatedWithDriver(username);
        List<RaceDTO> list1 = new ArrayList<>();
        for (Race race: list) {
            list1.add(new RaceDTO(race));
        }
        return Response.
                ok().
                entity(GSON.toJson(list1)).
                build();
    }

    //us 4 admins can create races.
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("create")
    public Response createRace(String race){
        RaceDTO raceDTO = GSON.fromJson(race, RaceDTO.class);
        RaceDTO createdRace = FACADE.createRace(raceDTO);
        return Response
                .ok()
                .entity(GSON.toJson(createdRace))
                .build();
    }
}
