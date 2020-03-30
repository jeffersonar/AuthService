package br.inf.datum.AuthService.dto;

import java.io.Serializable;

public class MessageDTO implements Serializable {

    private String mensagem;

    public MessageDTO(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }



}
