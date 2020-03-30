package br.inf.datum.AuthService.dto;

import br.inf.datum.AuthService.entity.Phone;
import br.inf.datum.AuthService.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

public class PhoneDTO implements Serializable {

    private String number;

    private String ddd;

    public PhoneDTO() {

    }
    public PhoneDTO(Phone phone) {
        this.number = phone.getNumber();
        this.ddd = phone.getDdd();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }
}
