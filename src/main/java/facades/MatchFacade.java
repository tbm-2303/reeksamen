package facades;

import com.google.gson.JsonObject;
import dtos.LocationDTO;
import dtos.MatchDTO;
import dtos.PlayerDTO;
import dtos.UserDTO;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import errorhandling.EntityNotFoundException;
import javassist.NotFoundException;
import security.errorhandling.AuthenticationException;
import utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class MatchFacade {

    private static EntityManagerFactory emf;
    private static MatchFacade instance;

    private MatchFacade() {
    }

    /**
     * @param _emf
     * @return the instance of this facade.
     */
    public static MatchFacade getMatchFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MatchFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    //us4
    public PlayerDTO createPlayer(PlayerDTO playerDTO, String username) {
        EntityManager em = emf.createEntityManager();
        try {
            Player player = new Player(playerDTO.getName(), playerDTO.getPhone(),
                    playerDTO.getEmail(), playerDTO.getStatus());

            TypedQuery<User> query = em.createQuery("SELECT u FROM User u where u.userName = '" + username + "'", User.class);
            User user = query.getSingleResult();
            player.addUser(user);
            em.getTransaction().begin();
            em.persist(player);
            em.getTransaction().commit();
            return new PlayerDTO(player);
        } finally {
            em.close();
        }
    }

    public MatchDTO createMatch(MatchDTO matchDTO, int locationid) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        try {
            Location location = em.find(Location.class, locationid);
            if (location == null) {
                throw new EntityNotFoundException("there is no location with that id");
            }
            Match match = new Match(matchDTO.getOpponent(), matchDTO.getJudge(), matchDTO.getType(), matchDTO.getInDoor());
            location.addMatch(match);

            em.getTransaction().begin();
            em.merge(location);
            em.persist(match);
            em.getTransaction().commit();
            return matchDTO;
        } finally {
            em.close();
        }
    }

    public LocationDTO createLocation(LocationDTO locationDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Location location = new Location(locationDTO.getAddress(), locationDTO.getCity(), locationDTO.getCon(), locationDTO.getName());
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
            return new LocationDTO(location);
        } finally {
            em.close();
        }
    }

    //us 2
    public List<Match> getMyMatches(String username) {
        EntityManager em = getEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u where u.userName = '" + username + "'", User.class);
        User user = query.getSingleResult();
        int id = user.getId();
        TypedQuery<Player> query1 = em.createQuery("SELECT p FROM Player p where p.user.id = '" + id + "'", Player.class);
        Player player = query1.getSingleResult();
        return player.getMatches();
    }


    public List<Match> getMatchesByLocation(int locationid) {
        EntityManager em = getEntityManager();
        TypedQuery<Match> query = em.createQuery("SELECT m FROM Match m where m.location.id = '" + locationid + "'", Match.class);
        return query.getResultList();
    }

    public List<Match> getAll() {
        EntityManager em = getEntityManager();
        TypedQuery<Match> query = em.createQuery("SELECT m FROM Match m", Match.class);
        return query.getResultList();
    }

    public List<Player> getAllPlayers() {
        EntityManager em = getEntityManager();
        TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p", Player.class);
        return query.getResultList();
    }


    public Match getById(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Match match = em.find(Match.class, id);
        if (match == null)
            throw new EntityNotFoundException("Match with ID: " + id + " was not found");
        return match;
    }

    public MatchDTO update(MatchDTO match) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Match m = em.find(Match.class, match.getId());
        if (m == null) {
            throw new EntityNotFoundException("Match with ID: " + match.getId() + " was not found");
        }
        m.setOpponent(match.getOpponent());
        m.setJudge(match.getJudge());
        m.setType(match.getType());
        m.setInDoor(match.getInDoor());
        //m.setLocation(match.getLocation());


        em.getTransaction().begin();
        Match updated = em.merge(m);
        em.getTransaction().commit();
        return match;
    }

    public Player deletePlayer(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Player player = em.find(Player.class, id);
        if (player == null)
            throw new EntityNotFoundException("Could not delete player with id: " + id);

        User user = player.getUser();
        try {
            em.getTransaction().begin();
            List<Match> matches = player.getMatches();
            for (Match m : matches) {
                m.removePlayer(player);
            }
            player.removeUser(user);
           // em.merge(player); //dunno if i need this.
            em.remove(player);
            em.getTransaction().commit();
            return player;
        } finally {
            em.close();
        }
    }




}

