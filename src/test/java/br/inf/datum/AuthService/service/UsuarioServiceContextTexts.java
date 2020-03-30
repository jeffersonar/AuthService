package br.inf.datum.AuthService.service;

import br.inf.datum.AuthService.dto.PhoneDTO;
import br.inf.datum.AuthService.dto.UserCadDTO;
import br.inf.datum.AuthService.entity.Phone;

import java.util.ArrayList;

public abstract class UsuarioServiceContextTexts {

    public UserCadDTO getUserCadDTO(){
        UserCadDTO userCadDTO = new UserCadDTO();
        userCadDTO.setEmail("teste@teste.com.br");
        userCadDTO.setName("teste teste");
        userCadDTO.setPassword("123");
        PhoneDTO phone = new PhoneDTO();
        phone.setDdd("51");
        phone.setNumber("88888-8888");
        userCadDTO.setPhone(new ArrayList<>());
        userCadDTO.getPhone().add(phone);
        return userCadDTO;
    }
}
