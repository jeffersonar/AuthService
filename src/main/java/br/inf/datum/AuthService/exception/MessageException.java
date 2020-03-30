package br.inf.datum.AuthService.exception;

import javax.servlet.http.HttpServletResponse;

public class MessageException extends RuntimeException {

    private Integer codeStatus;

    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
        this.codeStatus = HttpServletResponse.SC_BAD_REQUEST;
    }
    public MessageException(int code,String message) {
        super(message);
        this.codeStatus=code;
    }

    public Integer getCodeStatus() {
        return codeStatus;
    }
}
