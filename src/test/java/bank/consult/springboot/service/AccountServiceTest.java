package bank.consult.springboot.service;

import bank.consult.springboot.dto.AccountDTO;
import bank.consult.springboot.entity.AccountEntity;
import bank.consult.springboot.entity.UserEntity;
import bank.consult.springboot.enums.AccountType;
import bank.consult.springboot.repository.AccountRepository;
import bank.consult.springboot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {
    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        userRepository = mock(UserRepository.class);
        accountService = new AccountService(accountRepository, userRepository);
    }


// teste de cadastro de conta
    @Test
    void testCreateAccount_Success() {
        String firstName = "João";
        String lastName = "Silva";
        String email = "joao@teste.com";
        String phone = "999999999";
        double initialBalance = 500.0;
        AccountType accountType = AccountType.CORRENTE;
        when(userRepository.findByFirstNameAndLastName(firstName, lastName))
                .thenReturn(Collections.emptyList());
        UserEntity savedUser = new UserEntity();
        savedUser.setId(1L);
        savedUser.setFirstName(firstName);
        savedUser.setLastName(lastName);
        savedUser.setEmail(email);
        savedUser.setPhone(phone);
        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(savedUser);
        AccountEntity savedAccount = new AccountEntity();
        savedAccount.setId(1L);
        savedAccount.setUser(savedUser);
        savedAccount.setAccountType(accountType);
        savedAccount.setBalance(BigDecimal.valueOf(initialBalance));
        savedAccount.setCreatedAt(java.time.LocalDateTime.now());
        when(accountRepository.save(any(AccountEntity.class)))
                .thenReturn(savedAccount);
        AccountDTO result = accountService.createAccount(
                firstName,
                lastName,
                accountType,
                initialBalance,
                phone,
                email
        );
        assertNotNull(result);
        assertEquals("João", result.getUserId() != null ? savedUser.getFirstName() : null);
        assertEquals("CORRENTE", result.getAccountType());
        assertEquals(BigDecimal.valueOf(initialBalance), result.getBalance());
        verify(userRepository).save(any(UserEntity.class));
        verify(accountRepository).save(any(AccountEntity.class));
    }

    // teste de deposito de conta
    @Test
    void deposit_Success() {
        Long accountId = 1L;
        BigDecimal depositAmount = BigDecimal.valueOf(200);
        UserEntity user = new UserEntity();
        user.setFirstName("Lucas");
        AccountEntity account = new AccountEntity();
        account.setId(accountId);
        account.setUser(user);
        account.setBalance(BigDecimal.valueOf(500));
        account.setAccountType(AccountType.CORRENTE);
        account.setCreatedAt(java.time.LocalDateTime.now());
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(account);
        AccountDTO result = accountService.deposit(accountId, depositAmount);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(700), result.getBalance());
        assertTrue(result.getSuccessMessage().contains("Depósito de R$"));
        verify(accountRepository).save(account);
    }

    // teste de saque de conta
    @Test
    void withdraw_Success() {
        Long accountId = 1L;
        BigDecimal withdrawAmount = BigDecimal.valueOf(100);
        UserEntity user = new UserEntity();
        user.setFirstName("Ana");
        AccountEntity account = new AccountEntity();
        account.setId(accountId);
        account.setUser(user);
        account.setBalance(BigDecimal.valueOf(500));
        account.setAccountType(AccountType.POUPANCA);
        account.setCreatedAt(java.time.LocalDateTime.now());
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(account);
        AccountDTO result = accountService.withdraw(accountId, withdrawAmount);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(400), result.getBalance());
        assertTrue(result.getSuccessMessage().contains("Saque de R$"));
        verify(accountRepository).save(account);
    }

    // teste de saque sem saldo
    @Test
    void withdraw_InsufficientBalance_ThrowsException() {
        UserEntity user = new UserEntity();
        user.setFirstName("Pedro");
        AccountEntity account = new AccountEntity();
        account.setId(1L);
        account.setUser(user);
        account.setBalance(BigDecimal.valueOf(50));
        account.setAccountType(AccountType.CORRENTE);
        account.setCreatedAt(java.time.LocalDateTime.now());
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                accountService.withdraw(1L, BigDecimal.valueOf(100))
        );
        assertEquals("Saldo insuficiente para realizar o saque.", exception.getMessage());
    }

    // teste de msg de conta
    @Test
    void getBalanceMessage_Success() {
        UserEntity user = new UserEntity();
        user.setFirstName("Mariana");
        AccountEntity account = new AccountEntity();
        account.setId(1L);
        account.setUser(user);
        account.setBalance(BigDecimal.valueOf(1500));
        account.setCreatedAt(java.time.LocalDateTime.now());
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        String message = accountService.getBalanceMessage(1L);
        assertEquals("Mariana, seu saldo é de 1500", message);
    }
}