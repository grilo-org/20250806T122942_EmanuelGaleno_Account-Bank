package bank.consult.springboot.repository;

import bank.consult.springboot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Metodo para verificar se user ja existe e dps criar uma conta
    @Query("SELECT u FROM UserEntity u " +
            "WHERE LOWER(u.firstName) = LOWER(:firstName) " +
            "AND LOWER(u.lastName) = LOWER(:lastName)")
    List<UserEntity> findByFirstNameAndLastName(String firstName, String lastName);

    // Metodo para buscar usuario pelo primeiro nome, sobrenome e ID
    @Query("SELECT u FROM UserEntity u " +
            "WHERE LOWER(u.firstName) = LOWER(:firstName) " +
            "AND LOWER(u.lastName) = LOWER(:lastName) " +
            "AND u.id = :id")
    List<UserEntity> findByFirstNameAndLastNameAndId(String firstName, String lastName, Long id);
}

