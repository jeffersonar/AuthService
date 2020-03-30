package br.inf.datum.AuthService.service;

import br.inf.datum.AuthService.dto.UserCadDTO;
import br.inf.datum.AuthService.dto.UserDTO;
import br.inf.datum.AuthService.entity.Phone;
import br.inf.datum.AuthService.entity.User;
import br.inf.datum.AuthService.exception.MessageException;
import br.inf.datum.AuthService.repository.PhoneRepository;
import br.inf.datum.AuthService.repository.UserRepository;
import br.inf.datum.AuthService.util.SegurancaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Override
    public UserDTO salvar(UserCadDTO userCad) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        validarUserCad(userCad);
        User user  = User.convertUser(userCad);
        user.setId(SegurancaUtil.createUUid());
        user.setCreated(new Date());
        user.setPassword(SegurancaUtil.encriptSHA256(user.getPassword()));
        List<Phone> phones = user.getPhones();
        user.setPhones(null);
        User newUser = userRepository.saveAndFlush(user);
        if(phones!=null && !phones.isEmpty()){
            phones.stream().forEach(p->p.setIdUser(newUser.getId()));
        }
        phoneRepository.saveAll(phones);
        newUser.setPhones(phones);
        UserDTO userDTO = new UserDTO(newUser);
        return userDTO;
    }

    private void validarUserCad(UserCadDTO userCad) {
        if(userCad.getName()==null || userCad.getName().isBlank()){
            this.mensagemCamposObrigatorio("name");
        }else if(userCad.getEmail()==null || userCad.getEmail().isBlank()){
            this.mensagemCamposObrigatorio("email");
        }else if(userCad.getPassword()==null || userCad.getPassword().isBlank()){
            this.mensagemCamposObrigatorio("password");
        }else if(null != validarEmail(userCad)){
            this.mensagemEmailDuplcado("E-mail");
        }
    }

    private void mensagemEmailDuplcado(String campo) {
        throw new MessageException(HttpStatus.CONFLICT.value(),campo.concat(" já existente!"));
    }

    private User validarEmail(UserCadDTO userCad) {
        return userRepository.findByEmail(userCad.getEmail());
    }

    public void mensagemCamposObrigatorio(String campo){
        throw new MessageException(HttpStatus.UNPROCESSABLE_ENTITY.value(),campo.concat(" é obrigatorio"));
    }

    public UserDTO validarUser(String token, String idUser) {
        User user = userRepository.findByTokenAndId(token,idUser);
        if(user==null){
            throw new MessageException(HttpServletResponse.SC_UNAUTHORIZED,"Não autorizado");
        }

        UserDTO userDTO = new UserDTO(user);
        return userDTO;
    }
}
