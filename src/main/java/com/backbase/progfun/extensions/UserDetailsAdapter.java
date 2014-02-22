package com.backbase.progfun.extensions;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.backbase.progfun.account.Account;

/**
 * Adapts the {@link Account} to the {@link UserDetails}.
 * This way the {@link Account} can be used for core user information.
 */
public class UserDetailsAdapter implements UserDetails {

    private Account account;

    public UserDetailsAdapter(Account account) {
        this.account = account;
    }

    public String getFirstName() {
        return account.getFirstName();
    }

    public String getLastName() {
        return account.getLastName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_PRE_AUTH_USER");
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
