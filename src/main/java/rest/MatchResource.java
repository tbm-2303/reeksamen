package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.LocationDTO;
import dtos.MatchDTO;
import dtos.PlayerDTO;
import entities.Match;
import entities.Player;
import errorhandling.EntityNotFoundException;
import facades.MatchFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
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
    @Path("getMyMatches/{username}")
    //@RolesAllowed("user")
    public Response getMyMatches(@PathParam("username") String username) {
        List<Match> matches = FACADE.getMyMatches(username);
        List<MatchDTO> matchDTOS = new ArrayList<>();
        for (Match m : matches) {
            matchDTOS.add(new MatchDTO(m));
        }
        return Response
                .ok()
                .entity(GSON.toJson(matchDTOS))
                .build();
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("getByLocation/{locationid}")
    //@RolesAllowed("user")
    public Response getByLocation(@PathParam("locationid") Integer locationid) {
        List<Match> matches = FACADE.getMatchesByLocation(locationid);
        List<MatchDTO> matchDTOS = new ArrayList<>();
        for (Match match : matches) {
            matchDTOS.add(new MatchDTO(match));
        }
        return Response
                .ok()
                .entity(GSON.toJson(matchDTOS))
                .build();
    }

    ;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create/{locationid}")
    public Response createMatch(String match, @PathParam("locationid") Integer locationid) throws EntityNotFoundException {
        MatchDTO matchDTO = GSON.fromJson(match, MatchDTO.class);
        MatchDTO created = FACADE.createMatch(matchDTO, locationid);
        return Response
                .ok()
                .entity(GSON.toJson(created))
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createPlayer/{username}")
    public Response createPlayer(String player, @PathParam("username") String username) throws EntityNotFoundException {
        PlayerDTO playerDTO = GSON.fromJson(player, PlayerDTO.class);
        PlayerDTO created = FACADE.createPlayer(playerDTO, username);
        return Response
                .ok()
                .entity(GSON.toJson(created))
                .build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createLocation/")
    public Response createPlayer(String location) throws EntityNotFoundException {
        LocationDTO locationDTO = GSON.fromJson(location, LocationDTO.class);
        LocationDTO created = FACADE.createLocation(locationDTO);
        return Response
                .ok()
                .entity(GSON.toJson(created))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})

    public Response update(@PathParam("id") int id, String content) throws EntityNotFoundException {
        MatchDTO dto = GSON.fromJson(content, MatchDTO.class);
        dto.setId(id);
        MatchDTO updated = FACADE.update(dto);
        return Response.ok().entity(GSON.toJson(updated)).build();
    }

    @DELETE
    @Path("delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") int id) throws EntityNotFoundException {
        PlayerDTO deleted = new PlayerDTO(FACADE.deletePlayer(id));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(deleted))
                .build();
    }
}