package io.smartcat.spring.cassandra.showcase.test.cassandra;

import java.time.Duration;

import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import io.smartcat.spring.cassandra.showcase.test.cassandra.stub.SessionProxy;

@Configuration
@Profile({"test"})
public class TestCassandraConfig implements DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestCassandraConfig.class);

    private static final String CQL = "db.cql";

    @Value("${cassandra.startupTimeoutInSeconds}")
    private long startupTimeoutInSeconds;

    @Value("${cassandra.contactPoints}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    private static Cluster cluster;
    private static Session session;
    private static SessionProxy sessionProxy;

    @Bean
    public Session session() throws Exception {
        if (session == null) {
            initialize();
        }

        if (sessionProxy == null) {
            sessionProxy = new SessionProxy(session);
        }

        return sessionProxy;
    }

    @Bean
    public TestApplicationContext testApplicationContext() {
        return new TestApplicationContext();
    }

    private void initialize() throws Exception {
        LOGGER.info("Starting embedded cassandra server");
        EmbeddedCassandraServerHelper.startEmbeddedCassandra("cassandra-unit.yaml",
            Duration.ofSeconds(startupTimeoutInSeconds).toMillis());

        LOGGER.info("Connect to embedded db");
        cluster = Cluster.builder().addContactPoints(contactPoints).withPort(port).build();
        session = cluster.connect();

        LOGGER.info("Initialize keyspace");
        final CQLDataLoader cqlDataLoader = new CQLDataLoader(session);
        cqlDataLoader.load(new ClassPathCQLDataSet(CQL, false, true, keyspace));
    }

    @Override
    public void destroy() throws Exception {
        if (cluster != null) {
            cluster.close();
            cluster = null;
        }
    }
}
