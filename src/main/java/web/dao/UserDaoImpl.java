package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void add(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public User getUserById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("select u from User u where u.id = :id").setParameter("id", id);
        User user = (User) query.getSingleResult();
        entityManager.close();
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<User> users = entityManager.createQuery("from User").getResultList();
        entityManager.close();
        return users;
    }

    @Override
    public void delete(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("delete from User u where u.id = :id").setParameter("id", id);
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void update(long id, User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String name = user.getName();
        byte age = user.getAge();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("update User u set u.name = :name, u.age = :age where u.id = :id")
                .setParameter("id", id)
                .setParameter("name", name)
                .setParameter("age", age);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }
}