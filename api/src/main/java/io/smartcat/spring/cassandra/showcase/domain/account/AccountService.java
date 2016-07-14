package io.smartcat.spring.cassandra.showcase.domain.account;

import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.smartcat.spring.cassandra.showcase.adapter.http.account.dto.AccountDto;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountDto createAccount(
        final String firstName,
        final String lastName,
        final EmailAddress emailAddress,
        final String password,
        final String profileImageUrl
    ) {
        final Set<AccountRole> roles = new HashSet<>();
        roles.add(AccountRole.CUSTOMER);

        Account account = Account.createNew(emailAddress, password, firstName, lastName, URI.create(profileImageUrl),
                roles);

        accountRepository.createIfNotExists(account);

        return AccountDto.fromAccount(account);
    }

    public Optional<AccountDto> getBy(final EmailAddress emailAddress) {
        return accountRepository.accountOfEmail(emailAddress).map(AccountDto::fromAccount);
    }

}
