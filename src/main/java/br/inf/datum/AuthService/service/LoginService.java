package br.inf.datum.AuthService.service;

import br.inf.datum.AuthService.dto.LoginDTO;
import br.inf.datum.AuthService.dto.UserDTO;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jefferson
 * @descrition: Interface de LoginService
 */
public interface LoginService {

    public UserDTO logarUser(LoginDTO user) throws UnsupportedEncodingException, NoSuchAlgorithmException;


}
