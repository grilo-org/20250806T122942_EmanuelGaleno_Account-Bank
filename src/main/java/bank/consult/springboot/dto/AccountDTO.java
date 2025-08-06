package bank.consult.springboot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    @NotNull
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private String accountType;
    @NotNull
    private BigDecimal balance;
    @NotNull
    private LocalDateTime createdAt;
    private String successMessage;

    // Construtor sem successMessage
    public AccountDTO(Long id, Long userId, String accountType, BigDecimal balance, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.accountType = accountType;
        this.balance = balance;
        this.createdAt = createdAt;
    }// Mensagem de sucesso quando a conta for criada
}
