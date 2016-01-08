package io.smartcat.spring.cassandra.showcase.adapter.persistence.cassandra.account;

import java.net.URI;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import io.smartcat.spring.cassandra.showcase.domain.account.Account;
import io.smartcat.spring.cassandra.showcase.domain.account.AccountRole;
import io.smartcat.spring.cassandra.showcase.domain.account.EmailAddress;

@Table(name = AccountByEmail.TABLE_NAME)
public class AccountByEmail {

    public static final String TABLE_NAME = "account_by_email";

    public static final String EMAIL_COLUMN = "email_address";
    public static final String PASSWORD_COLUMN = "account_password";
    public static final String FIRST_NAME_COLUMN = "first_name";
    public static final String LAST_NAME_COLUMN = "last_name";
    public static final String REGISTERED_AT_COLUMN = "registered_at";
    public static final String ROLES_COLUMN = "roles";
    public static final String PROFILE_IMAGE_URL_COLUMN = "profile_image_url";

    @PartitionKey
    @Column(name = EMAIL_COLUMN)
    private String emailAddress;

    @Column(name = PASSWORD_COLUMN)
    private String password;

    @Column(name = FIRST_NAME_COLUMN)
    private String firstName;

    @Column(name = LAST_NAME_COLUMN)
    private String lastName;

    @Column(name = REGISTERED_AT_COLUMN)
    private Date registeredAt;

    @Column(name = ROLES_COLUMN)
    private Set<String> roles;

    @Column(name = PROFILE_IMAGE_URL_COLUMN)
    private String profileImageUrl;

    public AccountByEmail() {}

    public AccountByEmail(final Account account) {
        emailAddress = account.email().address();
        firstName = account.firstName();
        lastName = account.lastName();
        password = account.getPassword();
        roles = account.roles()
            .stream()
            .map(AccountRole::toString)
            .collect(Collectors.toSet());
        registeredAt = Date.from(account.registeredAt());
        profileImageUrl = account.profileImageUrl().toString();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(final Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(final Set<String> roles) {
        this.roles = roles;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(final String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Account toAccount() {
        final Set<AccountRole> accountRoles = roles
            .stream()
            .map(AccountRole::valueOf)
            .collect(Collectors.toSet());

        final Account account = Account.fromDatabase(new EmailAddress(emailAddress), password,
            firstName, lastName, registeredAt.toInstant(), URI.create(profileImageUrl),
            accountRoles);

        return account;
    }
}
