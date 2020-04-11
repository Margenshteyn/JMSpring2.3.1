package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDAO;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    @Override
    public boolean addUser(User user) {
        if (userDAO.getUserByLogin(user.getLogin()) != null) {
            return false;
        }
        userDAO.addUser(user);
        return true;
    }

    @Transactional
    @Override
    public boolean updateUser(User user, String password) {
        if (userDAO.validateUser(user.getLogin(), password)) {
            userDAO.updateUser(user);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public void deleteUser(String login) {
        userDAO.deleteUser(login);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsersList() {
        return userDAO.getAllUsers();
    }
}
