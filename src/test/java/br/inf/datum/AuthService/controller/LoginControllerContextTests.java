package br.inf.datum.AuthService.controller;

import br.inf.datum.AuthService.dto.LoginDTO;
import br.inf.datum.AuthService.dto.UserDTO;
import br.inf.datum.AuthService.service.TokenAuthenticationService;
import br.inf.datum.AuthService.util.SegurancaUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author: Jefferson
 * @description: Classe de contexto do teste
 * @Controller: LoginController
 */
public abstract class LoginControllerContextTests {


    protected UserDTO getUserDTO() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserDTO retorno = new UserDTO();
        retorno.setId(SegurancaUtil.createUUid());
        retorno.setName("Teste 123");
        retorno.setToken(TokenAuthenticationService.createToken(retorno.getName()));
        retorno.setCreated(new Date());
        retorno.setLastLogin(new Date());
        return retorno;
    }

    protected  LoginDTO getLoginDTO(){
        LoginDTO retorno = new LoginDTO();
        retorno.setEmail("teste@teste.com");
        retorno.setPassword("teste123");
        return retorno;
    }
}
