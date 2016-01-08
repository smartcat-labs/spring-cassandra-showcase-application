package io.smartcat.spring.cassandra.showcase.test.generators;

import static io.smartcat.spring.cassandra.showcase.test.generators.EmailAddressGenerators.givenAnyEmailAddress;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import io.smartcat.spring.cassandra.showcase.domain.account.Account;
import io.smartcat.spring.cassandra.showcase.domain.account.AccountRole;

public class AccountGenerators {

    public static String givenAnyLastName() {
        return "Doe";
    }

    public static String givenAnyFirstName() {
        return "Johnatan";
    }

    public static String givenAnyPassword() {
        return "somePassWord";
    }

    public static Set<AccountRole> givenAnyRoles() {
        return new HashSet<>(Arrays.asList(AccountRole.ADMINISTRATOR, AccountRole.CUSTOMER));
    }

    public static Set<AccountRole> givenRoleAdministrator() {
        return new HashSet<>(Arrays.asList(AccountRole.ADMINISTRATOR));
    }

    public static URI givenAnyImageUrl() {
        return URI.create("http://www.example.com/some/image.jpg");
    }

    public static Account givenAnyAccount() {
        return Account.createNew(
            givenAnyEmailAddress(),
            givenAnyPassword(),
            givenAnyFirstName(),
            givenAnyLastName(),
            givenAnyImageUrl(),
            givenAnyRoles());
    }
}
