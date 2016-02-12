package io.smartcat.spring.cassandra.showcase.test.cassandra.stub;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;

/**
 * ResultSetFuture implementation with only get() method defined.
 *
 * Used for tests to enable asynchronous calls executed synchronously.
 *
 */
public class ResultSetFutureStub implements ResultSetFuture {

    private final ResultSet resultSet;

    public ResultSetFutureStub(final ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public void addListener(final Runnable listener, final Executor executor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCancelled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDone() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet get() throws InterruptedException, ExecutionException {
        return resultSet;
    }

    @Override
    public ResultSet get(final long timeout, final TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException {
        return resultSet;
    }

    @Override
    public ResultSet getUninterruptibly() {
        return resultSet;
    }

    @Override
    public ResultSet getUninterruptibly(final long timeout, final TimeUnit unit)
        throws TimeoutException {
        return resultSet;
    }

    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }

}
