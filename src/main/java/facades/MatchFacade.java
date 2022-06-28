package facades;

import com.google.gson.JsonObject;
import dtos.MatchDTO;
import dtos.UserDTO;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import errorhandling.EntityNotFoundException;
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





    //us 2
    public List<MatchDTO> getMyMatches(String username){
        EntityManager em = getEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u where u.userName = '" + username +"'", User.class);
        User user = query.getSingleResult();
        int id = user.getId();
        TypedQuery<Player> query1 = em.createQuery("SELECT p FROM Player p where p.user.id = '" + id +"'", Player.class);
        Player player = query1.getSingleResult();
        List<Match> matches = player.getMatches();
        em.close();
        return MatchDTO.getDTOS(matches);
    }


    public MatchDTO create(MatchDTO matchDTO, int locationid) {
        EntityManager em = getEntityManager();
        Location location = em.find(Location.class, locationid);
        Match match = new Match(matchDTO.getOpponent(),matchDTO.getJudge(),matchDTO.getType(),matchDTO.getInDoor());
        location.addMatch(match);
        try {
            em.getTransaction().begin();
            em.merge(location);
            em.persist(match);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return matchDTO;
    }

    public List<Match> getMatchesByLocation(int locationid) {
        EntityManager em = getEntityManager();
        TypedQuery<Match> query = em.createQuery("SELECT m FROM Match m where m.location.id = '" + locationid +"'", Match.class);
        return query.getResultList();
    }

    public List<Match> getAll() {
        EntityManager em = getEntityManager();
        TypedQuery<Match> query = em.createQuery("SELECT m FROM Match m", Match.class);
        return query.getResultList();
    }

    public Match getById(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Match match = em.find(Match.class, id);
        if (match == null)
            throw new EntityNotFoundException("Match with ID: " + id + " was not found");
        return match;
    }

    public Match update(Match match) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Match m = em.find(Match.class, match.getId());
        if (m == null) {
            throw new EntityNotFoundException("Match with ID: " + match.getId() + " was not found");
        }
        m.setLocation(match.getLocation());
        m.setOpponent(match.getOpponent());
        m.setJudge(match.getJudge());
        m.setType(match.getType());
        m.setInDoor(match.getInDoor());

        em.getTransaction().begin();
        Match updated = em.merge(m);
        em.getTransaction().commit();
        return match;
    }

    public Match delete(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Match m = em.find(Match.class, id);
        if (m == null)
            throw new EntityNotFoundException("Could not remove Match with id: " + id);

        m.getLocation().getMatches().remove(m);
        m.setLocation(null);

        List<Player> players;
        players = m.getPlayers();

        if (players != null){
            for (Player p : players) {
                p.removeMatch(m);
            }
        }

        em.getTransaction().begin();
        em.merge(m);//not sure yet
        em.remove(m);
        em.getTransaction().commit();
        return m;
    }

}

