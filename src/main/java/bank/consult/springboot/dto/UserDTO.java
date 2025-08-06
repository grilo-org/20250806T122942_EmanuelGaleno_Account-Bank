package bank.consult.springboot.dto;


import org.jetbrains.annotations.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Nullable
    private String email;
    @Nullable
    private String phone;
    private List<AccountDTO> accounts;
}
