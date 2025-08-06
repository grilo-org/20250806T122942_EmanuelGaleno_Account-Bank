package bank.consult.springboot.repository;

import bank.consult.springboot.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> id(Long id);
}
