package br.inf.datum.AuthService.repository;

import br.inf.datum.AuthService.entity.Phone;
import br.inf.datum.AuthService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, BigInteger> {

}
