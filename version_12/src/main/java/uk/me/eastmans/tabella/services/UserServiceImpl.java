package uk.me.eastmans.tabella.services;

import uk.me.eastmans.tabella.domain.User;
import uk.me.eastmans.tabella.forms.UserCreateForm;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    private EntityManager em;

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
        return Optional.ofNullable(query.setParameter("email", email).getSingleResult());
    }

    @Override
    public Collection<User> getAllUsers() {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u ORDER BY u.email", User.class);
        return query.getResultList();
    }

    @Transactional
    public User create(UserCreateForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        //user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
        user.setRole(form.getRole());
        em.persist(user);
        return user;
    }

}
