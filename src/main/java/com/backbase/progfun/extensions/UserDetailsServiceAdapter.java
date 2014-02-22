package com.backbase.progfun.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.backbase.progfun.account.Account;
import com.backbase.progfun.account.AccountRepository;

/**
 * Adapts the {@link AccountRepository} to the {@link UserDetailsService} interface
 * so Spring Security can use it as an authentication source.
 */
@Component
@Primary
public class UserDetailsServiceAdapter implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username);

        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserDetailsAdapter(account);
    }

}
