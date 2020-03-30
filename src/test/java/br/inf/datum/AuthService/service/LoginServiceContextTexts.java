package br.inf.datum.AuthService.service;

import br.inf.datum.AuthService.dto.LoginDTO;
import br.inf.datum.AuthService.entity.User;
import br.inf.datum.AuthService.util.SegurancaUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public abstract class  LoginServiceContextTexts {

    protected User getUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = new User();
        user.setId(SegurancaUtil.createUUid());
        user.setEmail("jeff@jeff.com.br");
        user.setName("jefferson");
        user.setCreated(new Date());
        return user;
    }
    protected LoginDTO getLoginDTO() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("jeff@jeff.com.br");
        loginDTO.setPassword("123");
        return loginDTO;
    }

}
