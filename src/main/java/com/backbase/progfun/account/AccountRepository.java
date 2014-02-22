package com.backbase.progfun.account;

/**
 * Repository interfaces for {@link Account} instances.
 */
public interface AccountRepository {

    /**
     * Return an {@link Account} instance with an email address.
     * @param email an email address as a String
     * @return a {@link Account instance} or null if not found.
     */
    Account findByEmail(String email);

}
