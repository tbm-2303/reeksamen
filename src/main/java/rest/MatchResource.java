package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MatchDTO;
import dtos.PlayerDTO;
import dtos.UserDTO;
import entities.Match;
import entities.User;
import facades.MatchFacade;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("match")
public class MatchResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final MatchFacade FACADE = MatchFacade.getMatchFacade(EMF_Creator.createEntityManagerFactory());

    @Context
    private UriInfo context;
    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    //@RolesAllowed("user")
    public Response getAllMatches() {
        List<Match> matches = FACADE.getAll();
        List<MatchDTO> matchDTOS = new ArrayList<>();
        for (Match match : matches) {
            matchDTOS.add(new MatchDTO(match));
        }
        return Response
                .ok()
                .entity(GSON.toJson(matchDTOS))
                .build();
    }



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create/{locationid}")
    public Response createMatch(String match, @PathParam("locationid") Integer locationid) throws NotFoundException {
        MatchDTO matchDTO= GSON.fromJson(match, MatchDTO.class);
        MatchDTO created = FACADE.create(matchDTO, locationid);
        return Response
                .ok()
                .entity(GSON.toJson(created))
                .build();
    }

}
