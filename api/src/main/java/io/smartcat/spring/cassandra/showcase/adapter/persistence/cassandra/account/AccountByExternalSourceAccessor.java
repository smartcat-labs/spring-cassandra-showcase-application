package io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface AccountByExternalSourceAccessor {

    @Query("INSERT INTO account_by_external_source ("
            + "external_source_id, "
            + "external_source, "
            + "email_address"
        + ") VALUES ("
            + ":externalSourceId, "
            + ":externalSource, "
            + ":emailAddress"
        + ") IF NOT EXISTS;")
    Statement insertExternalSourceIfNotExists(
        @Param("externalSourceId") String externalSourceId,
        @Param("externalSource") String externalSource,
        @Param("emailAddress") String emailAddress);

    @Query("SELECT * FROM account_by_external_source WHERE external_source = :externalSource "
        + "AND external_source_id = :externalSourceId")
    Statement getAccountByExternalSource(@Param("externalSource") String externalSource,
        @Param("externalSourceId") String externalSourceId);
}
