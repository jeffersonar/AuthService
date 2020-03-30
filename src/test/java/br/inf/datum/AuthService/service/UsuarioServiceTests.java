package br.inf.datum.AuthService.service;

import br.inf.datum.AuthService.dto.UserCadDTO;
import br.inf.datum.AuthService.dto.UserDTO;
import br.inf.datum.AuthService.entity.User;
import br.inf.datum.AuthService.exception.MessageException;
import br.inf.datum.AuthService.repository.UserRepository;
import br.inf.datum.AuthService.util.SegurancaUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author: Jefferson
 * @description: Teste na camada serviço/regras
 * @Controller: LoginServiceImpl
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UsuarioServiceImpl.class)
public class UsuarioServiceTests extends UsuarioServiceContextTexts {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    /**
     * @case: caso o name esteja vazio retorna mensage status 422
     */
    @Test
    public void cadastrarUserNameNull(){
        MessageException exception = assertThrows(MessageException.class, () -> {
            usuarioServiceImpl.salvar(new UserCadDTO());
        });
        assertTrue("name é obrigatorio".equals(exception.getMessage()));
        assertTrue(HttpStatus.UNPROCESSABLE_ENTITY.value() == exception.getCodeStatus().intValue());
    }

    /**
     * @case: caso o email esteja vazio retorna mensage status 422
     */
    @Test
    public void cadastrarUserEmailNull(){
        UserCadDTO userCadDTO = new UserCadDTO();
        userCadDTO.setName("teste");
        MessageException exception = assertThrows(MessageException.class, () -> {
            usuarioServiceImpl.salvar(userCadDTO);
        });
        assertTrue("email é obrigatorio".equals(exception.getMessage()));
        assertTrue(HttpStatus.UNPROCESSABLE_ENTITY.value() == exception.getCodeStatus().intValue());
    }

    /**
     * @case: caso o senha esteja vazio retorna mensage status 422
     */
    @Test
    public void cadastrarUserSenhaNull(){
        UserCadDTO userCadDTO = new UserCadDTO();
        userCadDTO.setName("teste");
        userCadDTO.setEmail("oire@oi.com.br");
        MessageException exception = assertThrows(MessageException.class, () -> {
            usuarioServiceImpl.salvar(userCadDTO);
        });
        assertTrue("password é obrigatorio".equals(exception.getMessage()));
        assertTrue(HttpStatus.UNPROCESSABLE_ENTITY.value() == exception.getCodeStatus().intValue());
    }

    /**
     * @case: Email ja foi cadastrado retorna mensage status 409
     */
    @Test
    public void cadastrarUserEmailJaCadastrdo(){
        UserCadDTO userCadDTO = this.getUserCadDTO();
        User user =  User.convertUser(userCadDTO);
        when(userRepository.findByEmail(userCadDTO.getEmail())).thenReturn(user);
        MessageException exception = assertThrows(MessageException.class, () -> {
            usuarioServiceImpl.salvar(userCadDTO);
        });
        assertTrue("E-mail já existente!".equals(exception.getMessage()));
        assertTrue(HttpStatus.CONFLICT.value() == exception.getCodeStatus().intValue());
    }

    /**
     * @case: cadastro concluido com sucesso retorno 200
     */
    @Test
    public void cadastrarUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserCadDTO userCadDTO = this.getUserCadDTO();
        User user =  User.convertUser(userCadDTO);
        user.setId(SegurancaUtil.createUUid());
        user.setCreated(new Date());
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO userNovo =  usuarioServiceImpl.salvar(userCadDTO);
        assertTrue(userNovo.getEmail().equals(user.getEmail()));
    }
}
