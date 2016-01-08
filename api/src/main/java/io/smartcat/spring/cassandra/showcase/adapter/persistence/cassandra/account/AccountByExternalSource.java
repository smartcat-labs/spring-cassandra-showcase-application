package io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = AccountByExternalSource.TABLE_NAME)
public class AccountByExternalSource {

    public static final String TABLE_NAME = "account_by_external_source";

    public static final String EXTERNAL_SOURCE_ID_COLUMN = "external_source_id";
    public static final String EXTERNAL_SOURCE_COLUMN = "external_source";
    public static final String EMAIL_COLUMN = "email_address";

    @PartitionKey(0)
    @Column(name = EXTERNAL_SOURCE_ID_COLUMN)
    private String externalSourceId;

    @PartitionKey(1)
    @Column(name = EXTERNAL_SOURCE_COLUMN)
    private String externalSource;

    @Column(name = EMAIL_COLUMN)
    private String emailAddress;

    public AccountByExternalSource() {}

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getExternalSourceId() {
        return externalSourceId;
    }

    public void setExternalSourceId(final String externalSourceId) {
        this.externalSourceId = externalSourceId;
    }

    public String getExternalSource() {
        return externalSource;
    }

    public void setExternalSource(final String externalSource) {
        this.externalSource = externalSource;
    }
}
