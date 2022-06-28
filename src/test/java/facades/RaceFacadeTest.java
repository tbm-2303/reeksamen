package facades;
import entities.Car;
import entities.Race;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class RaceFacadeTest {

    private static EntityManagerFactory emf;
    private static RaceFacade facade;
    Race race;
    Race race2;
    Car car;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RaceFacade.getRaceFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }


    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Driver.deleteAllRows").executeUpdate();
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.createNamedQuery("Race.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();


            Timestamp ts = Timestamp.valueOf("2022-02-03 01:00:00");
            race = new Race("aha", "dasd", ts, 2);
            race2 = new Race("Radd", "dawf", ts, 1);
            car = new Car("dqw", "dqw", "dqw", "2222", "dwq", "rdwq");
            race2.addCar(car);
            em.persist(race);
            em.persist(race2);
            em.persist(car);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void create() {
        Timestamp ts = Timestamp.valueOf("2022-02-03 01:00:00");
        Race expected = new Race("disco fortnite race", "LA", ts, 2);
        Race actual = facade.test(expected);

        assertEquals(expected, actual);
    }


}