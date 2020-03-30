package br.inf.datum.AuthService.controller;

import br.inf.datum.AuthService.dto.LoginDTO;
import br.inf.datum.AuthService.dto.UserCadDTO;
import br.inf.datum.AuthService.dto.UserDTO;
import br.inf.datum.AuthService.exception.MessageException;
import br.inf.datum.AuthService.repository.UserRepository;
import br.inf.datum.AuthService.service.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/usuario")
public class UsuarioController extends GenericController {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void salvar(@RequestBody(required = false) String usuarioJson, HttpServletResponse response) throws Exception {
        try {
            validarRequestBody(usuarioJson);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            UserCadDTO loginDto = mapper.readValue(usuarioJson, UserCadDTO.class);
            UserDTO userDto = usuarioServiceImpl.salvar(loginDto);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(mapper.writeValueAsString(userDto));
            response.setCharacterEncoding("UTF-8");

        } catch (MessageException me) {
            this.gerarMensagemErro(response, me);
        }
    }

    @RequestMapping(method = RequestMethod.GET,path = "/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void salvar(@RequestHeader("Authorization") String token, @PathVariable("idUser") String idUser, HttpServletResponse response) throws Exception {
        try {
            validarRequestBody(idUser);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            UserDTO userDto = usuarioServiceImpl.validarUser(token,idUser);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(mapper.writeValueAsString(userDto));
            response.setCharacterEncoding("UTF-8");

        } catch (MessageException me) {
            this.gerarMensagemErro(response, me);
        }
    }
}
