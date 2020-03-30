package br.inf.datum.AuthService.entity;

import br.inf.datum.AuthService.dto.UserCadDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="auth_user")
public class User implements Serializable {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modified")
    private Date modified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_login")
    private Date lastLogin;

    @Column(name="token")
    private String token;

    @OneToMany(mappedBy="user")
    private List<Phone> phones;

    public static User convertUser(UserCadDTO userCad) {
        User user = new User();
        user.setName(userCad.getName());
        user.setEmail(userCad.getEmail());
        user.setPassword(userCad.getPassword());
        if(userCad.getPhones()!=null && !userCad.getPhones().isEmpty()){
            user.setPhones(new ArrayList<>());
            userCad.getPhones().stream().forEach(p->{
                Phone phone = new Phone();
                phone.setDdd(p.getDdd());
                phone.setNumber(p.getNumber());
                user.getPhones().add(phone);
            });

        }
        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
