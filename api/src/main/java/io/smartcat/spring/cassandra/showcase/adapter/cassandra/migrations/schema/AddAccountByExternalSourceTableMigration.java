package io.smartcat.spring.cassandra.showcase.adapter.cassandra.migrations.schema;

import com.datastax.driver.core.Statement;
import io.smartcat.migration.Migration;
import io.smartcat.migration.MigrationType;
import io.smartcat.migration.SchemaMigration;
import io.smartcat.migration.exceptions.MigrationException;
import io.smartcat.migration.exceptions.SchemaAgreementException;
import io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account.AccountSchemaCreator;

public class AddAccountByExternalSourceTableMigration extends SchemaMigration {

    public AddAccountByExternalSourceTableMigration(final int version) {
        super(version);
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

    private void createAccountByExternalSourceSchema() throws SchemaAgreementException {
        final Statement statement = AccountSchemaCreator.createAccountByExternalSourceTableIfNotExists();
        executeWithSchemaAgreement(statement);
    }
}
