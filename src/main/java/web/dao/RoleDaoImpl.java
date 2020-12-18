package web.dao;

import web.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> listRoles() {
        Query query = entityManager.createQuery("SELECT role FROM Role role ");
        return query.getResultList();
    }
}