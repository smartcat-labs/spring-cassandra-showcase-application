package io.smartcat.spring.cassandra.showcase.domain.account;

import java.util.Objects;

import org.apache.commons.validator.routines.EmailValidator;

public class EmailAddress {

    private String address;

    public EmailAddress(final String address) {
        this.setAddress(address);
    }

    public String address() {
        return this.address;
    }

    protected void setAddress(final String address) {
        if (!EmailValidator.getInstance().isValid(address)) {
            throw new IllegalArgumentException(
                    String.format("Provided email address '%s' is not valid.", address));
        }
        this.address = address.toLowerCase();
    }

    public boolean sameValueAs(final EmailAddress other) {
        return other != null && Objects.equals(this.address, other.address);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return sameValueAs((EmailAddress) o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.address);
    }

    @Override
    public String toString() {
        return this.address;
    }
}
