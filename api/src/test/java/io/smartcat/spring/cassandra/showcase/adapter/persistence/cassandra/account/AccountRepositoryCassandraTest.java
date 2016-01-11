package io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account;

import static io.smartcat.spring.cassandra.showcase.test.generators.AccountGenerators.givenAnyAccount;
import static io.smartcat.spring.cassandra.showcase.test.generators.AccountGenerators.givenAnyTwitterId;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.smartcat.spring.cassandra.showcase.domain.account.Account;
import io.smartcat.spring.cassandra.showcase.domain.account.AccountRepository;
import io.smartcat.spring.cassandra.showcase.test.cassandra.CassandraTestExecutionListener;
import io.smartcat.spring.cassandra.showcase.test.cassandra.TestCassandraConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(
    classes = {
        TestCassandraConfig.class,
        AccountRepositoryCassandraTest.TestConfig.class
    },
    initializers = ConfigFileApplicationContextInitializer.class)
@TestExecutionListeners({
    CassandraTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class })
public class AccountRepositoryCassandraTest {

    @Configuration
    @Profile("test")
    static class TestConfig {
        @Bean
        static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }

        @Bean(name = "accountRepositoryCassandra")
        public AccountRepository accountRepository() {
            return new AccountRepositoryCassandra();
        }
    }

    @Autowired
    private AccountRepository accountRepository;

    @Test public void
    accountCreated_whenAccountDateIsValid() {
        final Account account = givenAnyAccount();

        // when
        final boolean accountCreated = accountRepository.createIfNotExists(account);

        // then
        assertThat(accountCreated).isTrue();
    }

    @Test public void
    accountNotCreated_whenAccountAlreadyExists() {
        final Account account = givenAnyAccount();
        accountRepository.createIfNotExists(account);

        // when
        final boolean accountCreated = accountRepository.createIfNotExists(account);

        // then
        assertThat(accountCreated).isFalse();
    }

    @Test public void
    accountByEmailReturned_whenAccountExistsInDatabase() {
        final Account account = givenAnyAccount();
        accountRepository.createIfNotExists(account);

        // when
        final Optional<Account> accountFromDatabaseOptional = accountRepository
            .accountOfEmail(account.email());

        // then
        assertThat(accountFromDatabaseOptional).isPresent();

        final Account accountFromDatabase = accountFromDatabaseOptional.get();
        assertAccountEquality(account, accountFromDatabase);
    }

    @Test public void
    accountByEmailNotReturned_whenAccountDoesNotExistsInDatabase() {
        final Account account = givenAnyAccount();

        // when
        final Optional<Account> accountFromDatabase = accountRepository
            .accountOfEmail(account.email());

        // then
        assertThat(accountFromDatabase).isEmpty();
    }

    @Test public void
    accountByTwitterIdLinkedToAccount_whenAccountAlreadyExists() {
        final Account account = givenAnyAccount();
        final String twitterId = givenAnyTwitterId();

        final boolean twitterAccountLinked = accountRepository.linkTwitterToAccount(twitterId,
            account);

        assertThat(twitterAccountLinked).isTrue();
    }

    @Test public void
    accountByTwitterIdNotLinkedToAccount_whenAccountAlreadyLinkedToTwitter() {
        final Account account = givenAnyAccount();
        final String twitterId = givenAnyTwitterId();
        accountRepository.linkTwitterToAccount(twitterId, account);

        final boolean twitterAccountLinked = accountRepository.linkTwitterToAccount(twitterId,
            account);

        assertThat(twitterAccountLinked).isFalse();
    }

    @Test public void
    accountByTwitterIdReturned_whenAccountWithTwitterIdExists() {
        final Account account = givenAnyAccount();
        final String twitterId = givenAnyTwitterId();
        accountRepository.createIfNotExists(account);
        accountRepository.linkTwitterToAccount(twitterId, account);

        final Optional<Account> accountByTwitterIdOptional = accountRepository
            .accountOfTwitterId(twitterId);

        assertThat(accountByTwitterIdOptional).isPresent();
        assertAccountEquality(account, accountByTwitterIdOptional.get());
    }

    @Test public void
    accountByTwitterIdNotReturned_whenAccountWithTwitterIdDoesNotExists() {
        final String twitterId = givenAnyTwitterId();

        final Optional<Account> accountByTwitterIdOptional = accountRepository
            .accountOfTwitterId(twitterId);

        assertThat(accountByTwitterIdOptional).isEmpty();
    }

    private void assertAccountEquality(final Account account, final Account accountFromDatabase) {
        assertThat(accountFromDatabase.firstName()).isEqualTo(account.firstName());
        assertThat(accountFromDatabase.lastName()).isEqualTo(account.lastName());
        assertThat(accountFromDatabase.email()).isEqualTo(account.email());

    }
}
