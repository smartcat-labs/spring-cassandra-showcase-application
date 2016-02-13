package io.smartcat.spring.cassandra.showcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.datastax.driver.core.Session;

import io.smartcat.migration.MigrationEngine;
import io.smartcat.migration.MigrationResources;
import io.smartcat.spring.cassandra.showcase.adapter.cassandra.migrations.data.AddAdminAccountDataMigration;

@EnableAutoConfiguration
@ComponentScan
public class Application {

    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(final String[] args) throws Exception {
        final ConfigurableApplicationContext context = SpringApplication.run(Application.class,
            args);

        migrateData(context);
    }

    /**
     * Add data migrations here. This method is executed right after application
     * is started so data will be migrated while application is running.
     *
     * @param context Spring context
     */
    private static void migrateData(final ConfigurableApplicationContext context) {
        LOGGER.info("Executing data migrations.");
        final Session session = (Session) context.getBean("session");

        final MigrationResources resources = new MigrationResources();
        resources.addMigration(new AddAdminAccountDataMigration(1));

        MigrationEngine.withSession(session).migrate(resources);
    }
}
