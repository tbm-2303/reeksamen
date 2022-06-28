package facades;

import entities.Driver;
import entities.User;
import errorhandling.EntityNotFoundException;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class DriverFacade  {
    private static DriverFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private DriverFacade() {
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static DriverFacade getDriverFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DriverFacade();
        }
        return instance;
    }

    public List<Driver> getDriversByCarID(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Driver> query = em.createQuery("SELECT d FROM Driver d WHERE d.car.id = '" + id + "'", Driver.class);
            return query.getResultList();

        } finally {
            em.close();
        }
    }
}
