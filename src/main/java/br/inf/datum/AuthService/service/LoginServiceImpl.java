package br.inf.datum.AuthService.service;

import br.inf.datum.AuthService.dto.LoginDTO;
import br.inf.datum.AuthService.dto.UserDTO;
import br.inf.datum.AuthService.entity.User;
import br.inf.datum.AuthService.exception.MessageException;
import br.inf.datum.AuthService.repository.UserRepository;
import br.inf.datum.AuthService.util.SegurancaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author Jefferson
 * @description: camada de serviço/regras do login
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final String USUÁRIO_E_OU_SENHA_INVÁLIDOS = "Usuário e/ou senha inválidos";

    @Autowired
    private UserRepository userRepository;

    /**
     * Metodo para validar se login
     * @param loginDto
     * @return userDTO
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @Override
    public UserDTO logarUser(LoginDTO loginDto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        validarEmailsenha(loginDto);
        loginDto.setPassword(SegurancaUtil.encriptSHA256(loginDto.getPassword()));
        User user = userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        validarUser(user);
        user.setLastLogin(new Date());
        user.setModified(new Date());
        user.setToken(TokenAuthenticationService.createToken(user.getEmail()));
        userRepository.save(user);
        UserDTO userDTO = new UserDTO(user);
        return userDTO;
    }

    /**
     * validar se usario é nulo
     * @param user
     */
    private void validarUser(User user) {
        if (user == null) {
            throw new MessageException(HttpServletResponse.SC_UNAUTHORIZED , USUÁRIO_E_OU_SENHA_INVÁLIDOS);
        }
    }

    /**
     * validar Usuario e senha
     * @param user
     */
    private void validarEmailsenha(LoginDTO user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new MessageException(USUÁRIO_E_OU_SENHA_INVÁLIDOS);
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new MessageException(USUÁRIO_E_OU_SENHA_INVÁLIDOS);
        }
    }
}
