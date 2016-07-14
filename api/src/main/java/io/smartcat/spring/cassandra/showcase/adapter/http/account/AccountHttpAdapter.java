package io.smartcat.spring.cassandra.showcase.adapter.http.account;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.smartcat.spring.cassandra.showcase.adapter.http.account.dto.AccountCreateRequest;
import io.smartcat.spring.cassandra.showcase.adapter.http.account.dto.AccountDto;
import io.smartcat.spring.cassandra.showcase.domain.account.AccountService;
import io.smartcat.spring.cassandra.showcase.domain.account.EmailAddress;

@RestController
public class AccountHttpAdapter {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "account", method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto register(@Valid @RequestBody final AccountCreateRequest request) {
        return accountService.createAccount(request.getFirstName(), request.getLastName(),
                new EmailAddress(request.getEmail()), request.getPassword(), request.profileImageUrl());
    }
}
