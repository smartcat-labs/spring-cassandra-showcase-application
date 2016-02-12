package io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import io.smartcat.spring.cassandra.showcase.domain.account.Account;
import io.smartcat.spring.cassandra.showcase.domain.account.AccountRepository;
import io.smartcat.spring.cassandra.showcase.domain.account.AccountRole;
import io.smartcat.spring.cassandra.showcase.domain.account.EmailAddress;
import io.smartcat.spring.cassandra.showcase.domain.account.ExternalSource;

@Repository("accountRepositoryCassandra")
public class AccountRepositoryCassandra implements AccountRepository {

    @Value("${cassandra.readConsistencyLevel}")
    private ConsistencyLevel readConsistencyLevel;

    @Value("${cassandra.writeConsistencyLevel}")
    private ConsistencyLevel writeConsistencyLevel;

    @Autowired
    public Session session;

    private Mapper<AccountByEmail> accountByEmailMapper;
    private Mapper<AccountByExternalSource> accountByExternalSourceMapper;
    private AccountByEmailAccessor accountByEmailAccessor;
    private AccountByExternalSourceAccessor accountByExternalSourceAccessor;

    @PostConstruct
    public void setupTable() {
        final MappingManager mappingManager = new MappingManager(session);
        accountByEmailMapper = mappingManager.mapper(AccountByEmail.class);
        accountByExternalSourceMapper = mappingManager.mapper(AccountByExternalSource.class);
        accountByEmailAccessor = mappingManager.createAccessor(AccountByEmailAccessor.class);
        accountByExternalSourceAccessor = mappingManager
            .createAccessor(AccountByExternalSourceAccessor.class);
    }

    @Override
    public Optional<Account> accountOfEmail(final EmailAddress email) {
        final Statement statement = accountByEmailAccessor
            .getAccountByEmailAddress(email.address());
        statement.setConsistencyLevel(readConsistencyLevel);
        final AccountByEmail accountByEmail =
            accountByEmailMapper.map(session.execute(statement)).one();

        return accountByEmail == null ? Optional.empty() : Optional.of(accountByEmail.toAccount());
    }

    @Override
    public boolean createIfNotExists(final Account account) {
        final Set<String> roles = account.roles()
            .stream()
            .map(AccountRole::toString)
            .collect(Collectors.toSet());
        final Statement statement = accountByEmailAccessor.insertAccountIfNotExists(
            account.email().address(),
            account.password(),
            account.firstName(),
            account.lastName(),
            Date.from(account.registeredAt()),
            roles,
            account.profileImageUrl().toString());
        statement.setConsistencyLevel(writeConsistencyLevel);
        final ResultSet result = session.execute(statement);

        return result.wasApplied();
    }

    @Override
    public Optional<Account> accountOfTwitterId(final String twitterId) {
        final Statement statement = accountByExternalSourceAccessor
            .getAccountByExternalSource(ExternalSource.TWITTER.name(), twitterId);
        statement.setConsistencyLevel(readConsistencyLevel);
        final ResultSet result = session.execute(statement);
        final AccountByExternalSource accountByTwitterId = accountByExternalSourceMapper
            .map(result).one();

        if (accountByTwitterId == null) {
            return Optional.empty();
        }

        return accountOfEmail(new EmailAddress(accountByTwitterId.getEmailAddress()));
    }

    @Override
    public boolean linkTwitterToAccount(final String twitterId, final Account account) {
        final Statement statement = accountByExternalSourceAccessor.insertExternalSourceIfNotExists(
            twitterId, ExternalSource.TWITTER.name(), account.email().address());

        statement.setConsistencyLevel(writeConsistencyLevel);
        final ResultSet result = session.execute(statement);

        return result.wasApplied();
    }
}
