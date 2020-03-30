package br.inf.datum.AuthService.controller;

import br.inf.datum.AuthService.dto.LoginDTO;
import br.inf.datum.AuthService.dto.MessageDTO;
import br.inf.datum.AuthService.dto.UserDTO;
import br.inf.datum.AuthService.exception.MessageException;
import br.inf.datum.AuthService.service.LoginServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController extends GenericController {

    @Autowired
    private LoginServiceImpl loginServiceImpl;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
        public void logar(@RequestBody(required = false) String usuarioJson, HttpServletResponse response) throws Exception{
        try{
            validarRequestBody(usuarioJson);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            LoginDTO loginDto = mapper.readValue(usuarioJson, LoginDTO.class);
            UserDTO userDto = loginServiceImpl.logarUser(loginDto);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(mapper.writeValueAsString(userDto));
            response.setCharacterEncoding("UTF-8");
        }catch (MessageException mensagem){
            this.gerarMensagemErro(response,mensagem);
        }
    }


}
