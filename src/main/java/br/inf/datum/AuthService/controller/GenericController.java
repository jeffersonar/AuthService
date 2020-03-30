package br.inf.datum.AuthService.controller;

import br.inf.datum.AuthService.dto.MessageDTO;
import br.inf.datum.AuthService.exception.MessageException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class GenericController {

    protected void validarRequestBody(String json) throws MessageException{
        if(StringUtils.isEmpty(json)){
            throw new MessageException(HttpServletResponse.SC_BAD_REQUEST,"Solicitação invalida!");
        }
    }

    protected void gerarMensagemErro(HttpServletResponse response, MessageException me) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(me.getCodeStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(mapper.writeValueAsString(new MessageDTO(me.getMessage())));
        response.setCharacterEncoding("UTF-8");
    }
}
