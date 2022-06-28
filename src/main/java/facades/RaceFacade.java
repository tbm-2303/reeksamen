package facades;

import dtos.RaceDTO;
import entities.Car;
import entities.Driver;
import entities.Race;
import entities.User;
import errorhandling.EntityNotFoundException;
import javassist.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;

public class RaceFacade {
    private static RaceFacade instance;
    private static EntityManagerFactory emf;


    private RaceFacade() {
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static RaceFacade getRaceFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RaceFacade();
        }
        return instance;
    }

    //us 1 users can see all available races.
    public List<Race> getAll() {
        EntityManager em = getEntityManager();
        TypedQuery<Race> query = em.createQuery("SELECT r FROM Race r", Race.class);
        return query.getResultList();
    }


    //us 4 admins can create races.
    public RaceDTO createRace(RaceDTO raceDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            //Timestamp ts = Timestamp.valueOf(raceDTO.getDate());
            Race race = new Race(raceDTO.getName(), raceDTO.getLocation(), raceDTO.getDate(), raceDTO.getDuration());
            em.getTransaction().begin();
            em.persist(race);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return raceDTO;
    }
    public Race test(Race Race1) {
        EntityManager em = emf.createEntityManager();
        try {
            //Timestamp ts = Timestamp.valueOf(raceDTO.getDate());
            Race race = new Race(Race1.getName(), Race1.getLocation(), Race1.getDate(), Race1.getDuration());
            em.getTransaction().begin();
            em.persist(race);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return Race1;
    }

    //us 3 get races associated with a driver.
    public List<Race> getRacesAssociatedWithDriver(String username) throws EntityNotFoundException {
        EntityManager em = getEntityManager();


        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName = '" + username + "'", User.class);
        User user = query.getSingleResult();
        int id = user.getId();
        TypedQuery<Driver> query1 = em.createQuery("SELECT d FROM Driver d WHERE d.user.id = '" + id + "'", Driver.class);
        Driver driver = query1.getSingleResult();

        Car car = driver.getCar();
        em.close();
        return car.getRaces();
    }


    //us 5 admins can update races.

    //us 6 admins can delete races.


    public Race getById(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Race race = em.find(Race.class, id);
        if (race == null)
            throw new EntityNotFoundException("Race with ID: " + id + " was not found");
        return race;
    }


    public long getCount() {
        EntityManager em = getEntityManager();
        try {
            return (long) em.createQuery("SELECT COUNT(r) FROM Race r").getSingleResult();
        } finally {
            em.close();
        }
    }


}
