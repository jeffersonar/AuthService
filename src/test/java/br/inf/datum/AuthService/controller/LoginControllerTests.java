package br.inf.datum.AuthService.controller;

import br.inf.datum.AuthService.service.LoginServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author: Jefferson
 * @description: Teste na camada de Controler da requisição
 * @Controller: LoginController
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTests extends LoginControllerContextTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginServiceImpl loginServiceImpl;

    /**
     * @case: Solicitar uma requisição com body vazio
     * @status: 400
     * @throws Exception
     */
    @Test
    public void loginRequisicaoVazia() throws Exception {
        when(loginServiceImpl.logarUser(null)).thenReturn(null);
        MvcResult mvcResult = this.mockMvc.perform(post("/login").content("")).andReturn();
        assertTrue(HttpServletResponse.SC_BAD_REQUEST == mvcResult.getResponse().getStatus());
    }

    /**
     * @case: Solicitar uma requisição com body preenchiado corretamente
     * retorno 200
     * @throws Exception
     */
    @Test
    public void loginRequisicaoOK() throws Exception {
        when(loginServiceImpl.logarUser(this.getLoginDTO())).thenReturn(this.getUserDTO());
        MvcResult mvcResult = this.mockMvc.perform(post("/login").content("{\"email\":\"teste@teste.com\",\"password\":\"teste123\"}"))
                .andReturn();
        assertTrue(HttpServletResponse.SC_OK == mvcResult.getResponse().getStatus());
    }


}
