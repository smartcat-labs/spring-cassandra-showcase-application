package io.smartcat.spring.cassandra.showcase.adapter.cassandra.migrations.data;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import io.smartcat.migration.Migration;
import io.smartcat.migration.MigrationException;
import io.smartcat.migration.MigrationType;
import io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account.AccountByEmail;
import io.smartcat.spring.cassandra.showcase.domain.account.Account;
import io.smartcat.spring.cassandra.showcase.domain.account.AccountRole;
import io.smartcat.spring.cassandra.showcase.domain.account.EmailAddress;

public class AddAdminAccountDataMigration extends Migration {

    private final static Logger LOGGER = LoggerFactory
        .getLogger(AddAdminAccountDataMigration.class);

    private Mapper<AccountByEmail> accountByEmailMapper;

    public AddAdminAccountDataMigration(final MigrationType type, final int version) {
        super(type, version);
    }

    @Override
    public String getDescription() {
        return "Adding admin accounts to account table.";
    }

    @Override
    public void execute() throws MigrationException {
        try {
            addAdminAccounts();
        } catch (final Exception e) {
            throw new MigrationException("Failed to execute AddAdminAccountDataMigration migration",
                e);
        }
    }

    private void addAdminAccounts() {
        final MappingManager mappingManager = new MappingManager(session);
        accountByEmailMapper = mappingManager.mapper(AccountByEmail.class);

        LOGGER.info("Creating admin account.");
        final AccountByEmail accountByEmail = new AccountByEmail(createAdminAccount());

        accountByEmailMapper.save(accountByEmail);
    }

    private Account createAdminAccount() {
        final Set<AccountRole> roles = new HashSet<>();
        roles.add(AccountRole.ADMINISTRATOR);
        roles.add(AccountRole.CUSTOMER);

        final Account adminAccount = Account.createNew(new EmailAddress("joe@example.com"),
            "pass@123^", "Joe", "Doe", URI.create("http://www/example.com/joe-profile.jpg"), roles);
        return adminAccount;
    }
}
