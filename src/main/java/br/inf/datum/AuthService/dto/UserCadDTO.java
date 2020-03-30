package br.inf.datum.AuthService.dto;

import br.inf.datum.AuthService.entity.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserCadDTO  implements Serializable {


    private String name;

    private String email;

    private String password;

    private List<PhoneDTO> phones;

    public UserCadDTO(){

    }
    public UserCadDTO(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        if(user.getPhones()!=null && !user.getPhones().isEmpty()){
            this.setPhones(new ArrayList<PhoneDTO>());
            user.getPhones().stream().forEach(phone-> this.getPhones().add(new PhoneDTO(phone)));
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
