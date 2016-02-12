package io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account;

import static com.datastax.driver.core.DataType.set;
import static com.datastax.driver.core.DataType.text;
import static com.datastax.driver.core.DataType.timestamp;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;

/**
 * Helper class to isolate all tables related to account.
 *
 */
public class AccountSchemaCreator {

    /**
     * Main account table which holds account by email address which is unique per account (natural
     * primary key).
     */
    public static Statement createTableIfNotExists() {
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

        return new SimpleStatement(createAccountByEmailTable);
    }

    /**
     * Account by external ID is reverse index to account table by external source. It has compound
     * primary key which is combination of external source and ID of that source. This table will
     * allow to fetch account by two queries and it is optimal since this happens only during sign
     * in.
     */
    public static Statement createAccountByExternalSourceTableIfNotExists() {
        final String createAccountByExternalSourceTable = SchemaBuilder
            .createTable(AccountByExternalSource.TABLE_NAME)
            .addPartitionKey(AccountByExternalSource.EXTERNAL_SOURCE_ID_COLUMN, text())
            .addPartitionKey(AccountByExternalSource.EXTERNAL_SOURCE_COLUMN, text())
            .addColumn(AccountByExternalSource.EMAIL_COLUMN, text()).ifNotExists().withOptions()
            .comment("Account email by external source.").buildInternal();

        return new SimpleStatement(createAccountByExternalSourceTable);
    }
}
