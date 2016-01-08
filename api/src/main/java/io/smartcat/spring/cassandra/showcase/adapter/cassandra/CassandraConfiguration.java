package io.smartcat.spring.cassandra.showcase.adapter.cassandra;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

import io.smartcat.migration.MigrationEngine;
import io.smartcat.migration.MigrationResources;
import io.smartcat.migration.MigrationType;
import io.smartcat.spring.cassandra.showcase.adapter.cassandra.migrations.schema.AddAccountByExternalSourceTableMigration;
import io.smartcat.spring.cassandra.showcase.adapter.cassandra.migrations.schema.InitializeSchema;

@Configuration
@Profile("default")
public class CassandraConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraConfiguration.class);

    @Value("${cassandra.contactPoints}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.username}")
    private String username;

    @Value("${cassandra.password}")
    private String password;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${cassandra.replicationStrategy}")
    private String replicationStrategy;

    @Value("${cassandra.replicationFactor}")
    private int replicationFactor;

    private Cluster cluster;

    @Bean
    public Session session(){
        final Session session = cluster().connect();
        createKeyspaceIfNotExists(session);
        useKeyspace(session);
        migrateSchema(session);

        return session;
    }

    private Cluster cluster(){
        LOGGER.info("Creating cluster with contact points: {} and port: {}", contactPoints, port);

        final String[] nodes = contactPoints.split(",");

        cluster = Cluster.builder().addContactPoints(nodes).withPort(port)
                .withCredentials(username, password).build();
        metadata(cluster);

        return cluster;
    }

    private void metadata(final Cluster cluster) {
        final Metadata metadata = cluster.getMetadata();
        LOGGER.info("Connected to cluster: {}", metadata.getClusterName());

        for (final Host host : metadata.getAllHosts()) {
            LOGGER.info("Datacenter: {} host: {}", host.getDatacenter(), host.getAddress());
        }
    }

    private void createKeyspaceIfNotExists(final Session session) {
        final String createKeyspace = String.format(
            "CREATE KEYSPACE IF NOT EXISTS %s  WITH replication = "
                + "{'class':'%s', 'replication_factor':%d};",
                keyspace, replicationStrategy, replicationFactor);
        session.execute(createKeyspace);
        session.execute(String.format("USE %s;", keyspace));
    }

    private void useKeyspace(final Session session) {
        session.execute(String.format("USE %s;", keyspace));
    }

    @PreDestroy
    public void closeCluster() {
        LOGGER.info("Disposing cluster {}", cluster.getClusterName());
        if (cluster != null){
            cluster.close();
            cluster = null;
        }
    }

    /**
     * Add schema migrations here. This method is executed right after keyspace
     * is created and it will execute migration in order they are defined.
     *
     * @param session Active cassandra session
     */
    private void migrateSchema(final Session session) {
        final MigrationResources resources = new MigrationResources();
        LOGGER.info("Executing schema migrations.");

        resources.addMigration(new InitializeSchema(MigrationType.SCHEMA, 1));
        resources.addMigration(new AddAccountByExternalSourceTableMigration(MigrationType.SCHEMA, 2));

        MigrationEngine.withSession(session).migrate(resources);
    }
}
