package uk.me.eastmans.tabella.services;

import org.springframework.security.core.authority.AuthorityUtils;
import uk.me.eastmans.tabella.domain.Role;
import uk.me.eastmans.tabella.domain.User;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public Role getRole() {
        return user.getRole();
    }

}