package io.smartcat.spring.cassandra.showcase.adapter.cassandra.migrations.schema;

import io.smartcat.migration.Migration;
import io.smartcat.migration.MigrationException;
import io.smartcat.migration.MigrationType;
import io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account.AccountSchemaCreator;

public class InitializeSchema extends Migration {

    public InitializeSchema(final MigrationType type, final int version) {
        super(type, version);
    }

    @Override
    public String getDescription() {
        return "Initializing smartcatshowcase database schema.";
    }

    @Override
    public void execute() throws MigrationException {
        try {
            createAccountSchema();

        } catch (final Exception e) {
            throw new MigrationException("Failed to execute InitializeSchema migration",
                e);
        }
    }

    private void createAccountSchema() {
        final AccountSchemaCreator accountSchemaCreator = new AccountSchemaCreator(session);
        accountSchemaCreator.createTableIfNotExists();
    }
}
