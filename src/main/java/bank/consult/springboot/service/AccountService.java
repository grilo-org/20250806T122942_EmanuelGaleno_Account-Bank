package bank.consult.springboot.service;

import bank.consult.springboot.dto.AccountDTO;
import bank.consult.springboot.entity.AccountEntity;
import bank.consult.springboot.entity.UserEntity;
import bank.consult.springboot.enums.AccountType;
import bank.consult.springboot.repository.AccountRepository;
import bank.consult.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    // Injeção via construtor (padrão recomendadíssimo)
    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    // Verifica se o user já existe no banco
    private boolean userExists(String firstName, String lastName) {
        List<UserEntity> existingUser = userRepository.findByFirstNameAndLastName(firstName, lastName);
        return !existingUser.isEmpty();
    }

    // Criação de uma conta e um usuário
    public AccountDTO createAccount(String firstName, String lastName, AccountType accountType, double initialBalance, String phone, String email) {
        if (userExists(firstName, lastName)) {
            throw new IllegalArgumentException("Já existe um usuário com esse nome.");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Saldo inicial não pode ser negativo.");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email != null ? email : "defaultEmail@example.com");
        userEntity.setPhone(phone != null ? phone : "0000000000");
        UserEntity savedUser = userRepository.save(userEntity);

        AccountEntity accountEntity = new AccountEntity(
                savedUser,
                accountType,
                BigDecimal.valueOf(initialBalance)
        );
        AccountEntity savedAccount = accountRepository.save(accountEntity);

        return new AccountDTO(
                savedAccount.getId(),
                savedAccount.getUser().getId(),
                savedAccount.getAccountType().name(),
                savedAccount.getBalance(),
                savedAccount.getCreatedAt(),
                "Conta criada com sucesso para " + savedAccount.getUser().getFirstName() + " com tipo de conta: " + savedAccount.getAccountType().name()
        );
    }

    // Aqui busca usuario pelo nome, sobrenome e ID com camel sensitive
    public UserEntity getUserByNameAndLastNameAndId(String firstName, String lastName, Long id) {
        List<UserEntity> users = userRepository.findByFirstNameAndLastNameAndId(firstName, lastName, id);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    // Verifica o saldo da conta
    public String getBalanceMessage(Long accountId) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        return account.getUser().getFirstName() + ", seu saldo é de " + account.getBalance();
    }

    // deposito de valor na conta
    public AccountDTO deposit(Long accountId, BigDecimal amount) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }
        BigDecimal updatedBalance = account.getBalance().add(amount);
        account.setBalance(updatedBalance);
        accountRepository.save(account);

        String successMessage = "Depósito de R$ " + amount + " realizado com sucesso! Saldo atual: R$ " + updatedBalance;

        return new AccountDTO(
                account.getId(),
                account.getUser().getId(),
                account.getAccountType().name(),
                updatedBalance,
                account.getCreatedAt(),
                successMessage
        );
    }

    // saque de valor na conta
    public AccountDTO withdraw(Long accountId, BigDecimal amount) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar o saque.");
        }
        BigDecimal updatedBalance = account.getBalance().subtract(amount);
        account.setBalance(updatedBalance);
        accountRepository.save(account);

        String successMessage = "Saque de R$ " + amount + " concluído com sucesso! Saldo restante de " + account.getUser().getFirstName() + ": R$ " + updatedBalance;

        return new AccountDTO(
                account.getId(),
                account.getUser().getId(),
                account.getAccountType().name(),
                updatedBalance,
                account.getCreatedAt(),
                successMessage
        );
    }
}