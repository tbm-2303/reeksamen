package facades;

import entities.Role;
import entities.User;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.junit.jupiter.api.Assertions.*;


class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;
    User user;
    User user1;
    Role userRole;


    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
    }


    @AfterAll
    public static void tearDownClass() {
    }


    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();

            user = new User("d","d","d");
            user1 = new User("ld","d","d");
            userRole = new Role("user");
            user.addRole(userRole);
            user1.addRole(userRole);
            em.persist(userRole);
            em.persist(user);
            em.persist(user1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    @Test
    void create() {
        User expected = new User("fe", "fe","fe");
        User actual   = facade.create(expected);
        assertEquals(expected, actual);
    }




}