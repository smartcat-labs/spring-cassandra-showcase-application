package io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account;

import static com.datastax.driver.core.DataType.set;
import static com.datastax.driver.core.DataType.text;
import static com.datastax.driver.core.DataType.timestamp;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;

public class AccountSchemaCreator {

    private final Session session;

    public AccountSchemaCreator(final Session session) {
        this.session = session;
    }

    public void createTableIfNotExists() {
        final String createAccountByEmailTable = SchemaBuilder
            .createTable(AccountByEmail.TABLE_NAME)
            .addPartitionKey(AccountByEmail.EMAIL_COLUMN, text())
            .addColumn(AccountByEmail.FIRST_NAME_COLUMN, text())
            .addColumn(AccountByEmail.LAST_NAME_COLUMN, text())
            .addColumn(AccountByEmail.PASSWORD_COLUMN, text())
            .addColumn(AccountByEmail.REGISTERED_AT_COLUMN, timestamp())
            .addColumn(AccountByEmail.ROLES_COLUMN, set(text()))
            .addColumn(AccountByEmail.PROFILE_IMAGE_URL_COLUMN, text())
            .ifNotExists().withOptions()
            .comment("Accounts in system by email.")
            .buildInternal();

        final Statement statement = new SimpleStatement(createAccountByEmailTable);
        statement.setConsistencyLevel(ConsistencyLevel.ALL);
        session.execute(statement);
    }
}
