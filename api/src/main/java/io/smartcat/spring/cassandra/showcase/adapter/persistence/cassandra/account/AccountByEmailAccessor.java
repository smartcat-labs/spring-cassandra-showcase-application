package io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account;

import java.util.Date;
import java.util.Set;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface AccountByEmailAccessor {

    @Query("INSERT INTO account_by_email ("
            + "email_address, "
            + "account_password, "
            + "first_name, "
            + "last_name, "
            + "registered_at, "
            + "roles, "
            + "profile_image_url"
        + ") VALUES ("
            + ":emailAddress, "
            + ":password, "
            + ":firstName, "
            + ":lastName, "
            + ":registeredAt, "
            + ":roles, "
            + ":profileImageUrl"
        + ") IF NOT EXISTS;")
    Statement insertAccountIfNotExists(
        @Param("emailAddress") String emailAddress,
        @Param("password") String password,
        @Param("firstName") String firstName,
        @Param("lastName") String lastName,
        @Param("registeredAt") Date registeredAt,
        @Param("roles") Set<String> roles,
        @Param("profileImageUrl") String profileImageUrl);
}
