package web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean validateUser(String login, String password) {
        User user = getUserByLogin(login);
        if (user != null) {
//            return getUserByLogin(login).getPassword().equals(password);
            return user.getPassword().equals(password);
        }
        return false;
    }

    @Override
    public User getUserByLogin(String login) {
        return sessionFactory.openSession().get(User.class, login);
    }

    @Override
    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void updateUser(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public void deleteUser(String login) {
        sessionFactory.getCurrentSession().delete(getUserByLogin(login));
    }

    @Override
    public List<User> getAllUsers() {
//        return (List<User>) sessionFactory.openSession().createQuery("From User").getResultList();
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User");
        return query.getResultList();
    }
}
