package io.smartcat.spring.cassandra.showcase.adapter.cassandra.migrations.schema;

import com.datastax.driver.core.Statement;
import io.smartcat.migration.Migration;
import io.smartcat.migration.MigrationType;
import io.smartcat.migration.SchemaMigration;
import io.smartcat.migration.exceptions.MigrationException;
import io.smartcat.migration.exceptions.SchemaAgreementException;
import io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account.AccountSchemaCreator;

public class InitializeSchema extends SchemaMigration {

    public InitializeSchema(final int version) {
        super(version);
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

    private void createAccountSchema() throws SchemaAgreementException {
        final Statement statement = AccountSchemaCreator.createTableIfNotExists();
        executeWithSchemaAgreement(statement);
    }
}
