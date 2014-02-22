package com.backbase.progfun.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    private List<Account> accounts;

    public InMemoryAccountRepository() {
        accounts = new ArrayList<Account>();
        init();
    }

    private void init() {
        Account account = new Account();
        account.setFirstName("Lisa");
        account.setLastName("Leslie");
        account.setEmail("lisa@gmail.com");
        account.setPassword("password");
        accounts.add(account);
    }

    @Override
    public Account findByEmail(String email) {
        Account result = null;
        for (Account account : accounts) {
            if (account.getEmail().equals(email)) {
                result = account;
                break;
            }
        }
        return result;
    }
}
