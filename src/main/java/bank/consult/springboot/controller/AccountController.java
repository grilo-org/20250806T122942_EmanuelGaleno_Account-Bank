package bank.consult.springboot.controller;


import bank.consult.springboot.dto.AccountDTO;
import bank.consult.springboot.entity.UserEntity;
import bank.consult.springboot.enums.AccountType;
import bank.consult.springboot.service.AccountService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // busca usuário pelo nome, sobrenome e ID
    @GetMapping("/user")
    public UserEntity getUserByNameAndId(@RequestParam @NotNull String firstName,
                                         @RequestParam @NotNull String lastName,
                                         @RequestParam Long id) {
        UserEntity user = accountService.getUserByNameAndLastNameAndId(firstName, lastName, id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Usuário não encontrado com o nome: " + firstName + ", sobrenome: " + lastName + " e ID: " + id);
        }
        return user;
    }

    // cria uma conta
    @PostMapping("/new_user")
    public AccountDTO createAccount(@RequestParam @NotNull String firstName,
                                    @RequestParam @NotNull String lastName,
                                    @RequestParam @NotNull String accountType,
                                    @RequestParam @NotNull double initialBalance,
                                    @RequestParam(required = false) String phone,
                                    @RequestParam(required = false) String email) {
        try {
            AccountType type = AccountType.valueOf(accountType.toUpperCase());
            return accountService.createAccount(firstName, lastName, type, initialBalance, phone, email);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // ver saldo da conta
    @GetMapping("/balance")
    public String getBalanceMessage(@RequestParam Long accountId) {
        try {
            return accountService.getBalanceMessage(accountId);  // Retorna a mensagem com o saldo
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // depósito
    @PostMapping("/deposit")
    public AccountDTO deposit(@RequestParam Long accountId, @RequestParam BigDecimal amount) {
        try {
            return accountService.deposit(accountId, amount);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    //  saque
    @PostMapping("/withdraw")
    public AccountDTO withdraw(@RequestParam Long accountId, @RequestParam BigDecimal amount) {
        try {
            return accountService.withdraw(accountId, amount);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
