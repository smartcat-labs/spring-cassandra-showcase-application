package io.smartcat.spring.cassandra.showcase.domain.account;

import java.net.URI;
import java.time.Instant;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {

    private final static int PASSWORD_MINIMUM_LENGTH = 8;

    private EmailAddress email;
    private String password;
    private String firstName;
    private String lastName;
    private Instant registeredAt;
    private URI profileImageUrl;
    private Set<AccountRole> roles;

    public static Account createNew(final EmailAddress email, final String password,
        final String firstName, final String lastName, final URI profileImageUrl,
        final Set<AccountRole> roles) {
        return new Account(email, password, firstName, lastName, Instant.now(), profileImageUrl,
            roles);
    }

    public static Account fromDatabase(final EmailAddress email, final String password,
        final String firstName, final String lastName, final Instant registeredAt,
        final URI profileImageUrl, final Set<AccountRole> roles) {
        return new Account(email, password, firstName, lastName, registeredAt, profileImageUrl,
            roles);
    }

    private Account(final EmailAddress email, final String password, final String firstName,
        final String lastName, final Instant registeredAt, final URI profileImageUrl,
        final Set<AccountRole> roles) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registeredAt = registeredAt;
        this.profileImageUrl = profileImageUrl;
        this.roles = roles;

    }

    public EmailAddress email() {
        return email;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public Set<AccountRole> roles() {
        return roles;
    }

    public Instant registeredAt() {
        return registeredAt;
    }

    /**
     * Enforce a policy of minimum length and required character types.
     *
     * @param password password to authenticate this user
     */
    protected void setPassword(final String password) {
        if (password.length() < PASSWORD_MINIMUM_LENGTH) {
            throw new IllegalArgumentException(String.format(
                "Password must be at least %s characters.", PASSWORD_MINIMUM_LENGTH));
        }

        // This works with ASCII upper and lower case letters only.
        final Pattern pattern = Pattern.compile("^(?=.*[^a-zA-Z])(?=.*[a-z])(?=.*[A-Z])\\S*$");
        final Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                "Password must contain at least a lower case letter, an upper case letter and "
                    + "non-letter.");
        }

        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public URI profileImageUrl() {
        return profileImageUrl;
    }

    public void updateProfileImageUrl(final URI profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public boolean isAdministrator() {
        return roles().contains(AccountRole.ADMINISTRATOR);
    }
}
