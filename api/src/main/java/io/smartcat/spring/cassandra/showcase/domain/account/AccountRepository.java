package io.smartcat.spring.cassandra.showcase.domain.account;

import java.util.Optional;

public interface AccountRepository {

    /**
     * Try to find account based on email if it exists.
     *
     * @param email EmailAddress of account.
     *
     * @return Optional with account if it exists.
     */
    Optional<Account> accountOfEmail(EmailAddress email);

    /**
     * Try to find account based on email if it exists.
     *
     * @param email EmailAddress of account.
     *
     * @return Optional with account if it exists.
     */
    Optional<Account> accountOfTwitterId(String twitterId);

    /**
     * Account will be created only if account with same email address does not
     * already exist
     *
     * @param account Account to be inserted
     * @return true if account was inserted, otherwise false
     */
    boolean createIfNotExists(Account account);

}
