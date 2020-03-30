package br.inf.datum.AuthService.repository;

import br.inf.datum.AuthService.dto.UserDTO;
import br.inf.datum.AuthService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    public User findByEmailAndPassword(String email, String password);


    public User findByEmail(String email);

    public User findByTokenAndId(String token, String idUser);
}
