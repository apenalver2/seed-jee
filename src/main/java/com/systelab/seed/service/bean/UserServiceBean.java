package com.systelab.seed.service.bean;

import com.systelab.seed.model.user.User;
import com.systelab.seed.service.UserService;
import com.systelab.seed.util.exceptions.UserNotFoundException;
import com.systelab.seed.util.security.AuthenticationTokenGenerator;
import com.systelab.seed.util.security.PasswordDigest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class UserServiceBean implements UserService {
    @PersistenceContext(unitName = "SEED")
    private EntityManager em;

    private AuthenticationTokenGenerator tokenGenerator;
    private PasswordDigest passwordDigest;

    @Inject
    void setAuthenticationTokenGenerator(AuthenticationTokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @Inject
    void setPasswordDigest(PasswordDigest passwordDigest) {
        this.passwordDigest = passwordDigest;
    }

    @Override
    public User getUser(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();

        TypedQuery<User> query = em.createNamedQuery(User.FIND_ALL, User.class);
        List<User> usersList = query.getResultList();
        for (User user : usersList) {
            users.add(user);
        }
        return users;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void create(User user) {
        em.persist(user);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Long id) throws UserNotFoundException {
        User u = em.find(User.class, id);
        if (u != null) {
            em.remove(u);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public String getToken(String uri, String login, String password) {
        User user = authenticate(login, password);
        return tokenGenerator.issueToken(user.getLogin(), user.getRole().toString(), uri);

    }

    private User authenticate(String login, String password) {
        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_LOGIN_PASSWORD, User.class);
        query.setParameter("login", login);
        query.setParameter("password", passwordDigest.digest(password));
        User user = query.getSingleResult();

        if (user == null)
            throw new SecurityException("Invalid user/password");

        return user;
    }

}
