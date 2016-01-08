package io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account;

import static io.smartcat.spring.cassandra.showcase.test.generators.AccountGenerators.givenAnyAccount;
import static org.assertj.core.api.Assertions.assertThat;

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
}
