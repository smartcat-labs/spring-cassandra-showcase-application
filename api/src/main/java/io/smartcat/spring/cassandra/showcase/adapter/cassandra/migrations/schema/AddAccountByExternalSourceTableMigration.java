package io.smartcat.spring.cassandra.showcase.adapter.cassandra.migrations.schema;

import io.smartcat.migration.Migration;
import io.smartcat.migration.MigrationType;
import io.smartcat.migration.exceptions.MigrationException;
import io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account.AccountSchemaCreator;

public class AddAccountByExternalSourceTableMigration extends Migration {

    public AddAccountByExternalSourceTableMigration(final MigrationType type, final int version) {
        super(type, version);
    }

    @Override
    public String getDescription() {
        return "Add account by external source migration.";
    }

    @Override
    public void execute() throws MigrationException {
        try {
            createAccountByExternalSourceSchema();

        } catch (final Exception e) {
            throw new MigrationException(
                "Failed to execute AddAccountByExternalSourceTableMigration migration", e);
        }
    }

    private void createAccountByExternalSourceSchema() {
        final AccountSchemaCreator accountSchemaCreator = new AccountSchemaCreator(session);
        accountSchemaCreator.createAccountByExternalSourceTableIfNotExists();
    }
}
