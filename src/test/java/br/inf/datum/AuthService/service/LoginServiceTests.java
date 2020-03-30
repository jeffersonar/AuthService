package br.inf.datum.AuthService.service;

import br.inf.datum.AuthService.dto.LoginDTO;
import br.inf.datum.AuthService.dto.UserDTO;
import br.inf.datum.AuthService.entity.User;
import br.inf.datum.AuthService.exception.MessageException;
import br.inf.datum.AuthService.repository.UserRepository;
import br.inf.datum.AuthService.service.LoginServiceImpl;
import br.inf.datum.AuthService.util.SegurancaUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author: Jefferson
 * @description: Teste na camada serviço/regras
 * @Controller: LoginServiceImpl
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LoginServiceImpl.class)
public class LoginServiceTests extends LoginServiceContextTexts{

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private LoginServiceImpl loginService;

    /**
     * @case: Caso o e-mail e senha não exista, retornar erro com status apropriado
     * mais a mensagem "Usuário e/ou senha inválidos"
     */
    @Test
    public void loginUsuarioESenhaIsNull(){
        LoginDTO user = new LoginDTO();
        MessageException exception = assertThrows(MessageException.class, () -> {
            loginService.logarUser(user);
        });
        String expectedMessage = "Usuário e/ou senha inválidos";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * @case: Caso o senha não exista, retornar erro com status apropriado
     * mais a mensagem "Usuário e/ou senha inválidos"
     */
    @Test
    public void loginSenhaIsNull(){
        LoginDTO user = new LoginDTO();
        user.setEmail("teste@teste.com.br");
        MessageException exception = assertThrows(MessageException.class, () -> {
            loginService.logarUser(user);
        });
        String expectedMessage = "Usuário e/ou senha inválidos";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * @case: Usuario é senha incorreto
     */
    @Test
    public void loginUsuarioESenhaIncorreto() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        LoginDTO loginDTO = getLoginDTO();
        when(userRepository.findByEmailAndPassword(loginDTO.getEmail(), SegurancaUtil.encriptSHA256(loginDTO.getPassword())))
                .thenReturn(null);
        MessageException exception = assertThrows(MessageException.class, () -> {
            loginService.logarUser(loginDTO);
        });
        String expectedMessage = "Usuário e/ou senha inválidos";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * @case: Usuario é senha correto"
     */
    @Test
    public void loginUsuarioESenhaCorreto() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        LoginDTO loginDTO = this.getLoginDTO();
        User user = this.getUser();
        when(userRepository.findByEmailAndPassword(loginDTO.getEmail(), SegurancaUtil.encriptSHA256(loginDTO.getPassword())))
                .thenReturn(user);
        UserDTO userDTO = loginService.logarUser(loginDTO);
        assertTrue(userDTO.getId().equals(new UserDTO(user).getId()));
    }


}
