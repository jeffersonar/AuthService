package br.inf.datum.AuthService.service;

import br.inf.datum.AuthService.dto.UserCadDTO;
import br.inf.datum.AuthService.dto.UserDTO;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface UsuarioService {


    public UserDTO salvar(UserCadDTO userCad) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
