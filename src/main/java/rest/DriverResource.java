package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Driver;
import entities.Race;
import facades.DriverFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Path("driver")
public class DriverResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final DriverFacade FACADE = DriverFacade.getDriverFacade(EMF_Creator.createEntityManagerFactory());
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("car/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllDriversByCarID(@PathParam("id") int id) {
        List<Driver> list = FACADE.getDriversByCarID(id);
        List<DriverDTO> list1 = new ArrayList<>();
        for (Driver driver: list) {
            list1.add(new DriverDTO(driver));

        }
        return Response
                .ok()
                .entity(GSON.toJson(list1))
                .build();
    }



}