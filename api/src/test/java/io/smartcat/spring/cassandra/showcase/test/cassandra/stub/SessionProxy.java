package io.smartcat.spring.cassandra.showcase.test.cassandra.stub;

import com.datastax.driver.core.CloseFuture;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Session proxy for synchronous execution of queries in order to keep tests
 * simple.
 *
 * Everything except executeAsync is pass-through call to actual session object.
 * Only executeAsync calls are wrapped into ResultSetFutureStub with synchronous
 * execution result.
 */
public class SessionProxy implements Session {

    private final Session session;

    public SessionProxy(final Session session) {
        this.session = session;
    }

    @Override
    public String getLoggedKeyspace() {
        return session.getLoggedKeyspace();
    }

    @Override
    public Session init() {
        return session.init();
    }

    @Override
    public ResultSet execute(final String query) {
        return session.execute(query);
    }

    @Override
    public ResultSet execute(final String query, final Object... values) {
        return session.execute(query, values);
    }

    @Override
    public ResultSet execute(final Statement statement) {
        return session.execute(statement);
    }

    @Override
    public ResultSetFuture executeAsync(final String query) {
        // This is executed as sync for testing purposes.
        return new ResultSetFutureStub(session.execute(query));
    }

    @Override
    public ResultSetFuture executeAsync(final String query, final Object... values) {
        // This is executed as sync for testing purposes.
        return new ResultSetFutureStub(session.execute(query, values));
    }

    @Override
    public ResultSetFuture executeAsync(final Statement statement) {
        // This is executed as sync for testing purposes.
        return new ResultSetFutureStub(session.execute(statement));
    }

    @Override
    public PreparedStatement prepare(final String query) {
        return session.prepare(query);
    }

    @Override
    public PreparedStatement prepare(final RegularStatement statement) {
        return session.prepare(statement);
    }

    @Override
    public ListenableFuture<PreparedStatement> prepareAsync(final String query) {
        return session.prepareAsync(query);
    }

    @Override
    public ListenableFuture<PreparedStatement> prepareAsync(final RegularStatement statement) {
        return session.prepareAsync(statement);
    }

    @Override
    public CloseFuture closeAsync() {
        return session.closeAsync();
    }

    @Override
    public void close() {
        session.close();
    }

    @Override
    public boolean isClosed() {
        return session.isClosed();
    }

    @Override
    public Cluster getCluster() {
        return session.getCluster();
    }

    @Override
    public State getState() {
        return session.getState();
    }

}
