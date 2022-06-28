package facades;

import com.google.gson.JsonObject;
import dtos.UserDTO;
import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import security.errorhandling.AuthenticationException;
import utils.Utility;

import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName = '" + username + "'", User.class);
            user = query.getSingleResult();
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }


    //todo: make more facade methods for persisting and updating data.
    public User create(User user) {
        EntityManager em = getEntityManager();
        Role role = em.find(Role.class, "user");
        user.addRole(role);
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return user;
    }
    public UserDTO deleteUser(String userName) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userName);
            em.remove(user);
            em.getTransaction().commit();
            return new UserDTO(user);
        } finally {
            em.close();
        }
    }

    public UserDTO updateUser(UserDTO userDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userDTO.getUserName());
            user.setUserPass(userDTO.getUserPass());
            em.merge(user);
            em.getTransaction().commit();
            return new UserDTO(user);
        } finally {
            em.close();
        }
    }

    public List<UserDTO> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<User> query = em.createQuery("SELECT u from User u", User.class);
            List<User> users = query.getResultList();
            em.getTransaction().commit();
            List<UserDTO> userDTOList = UserDTO.getDtos(users);
            return userDTOList;
        } finally {
            em.close();
        }
    }

    public JsonObject getRandomCatFact() {
        return Utility.fetchData("https://catfact.ninja/fact");
    }

    public JsonObject getRandomJoke() {
        return Utility.fetchData("https://api.chucknorris.io/jokes/random");
    }

}