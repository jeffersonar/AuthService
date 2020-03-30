package br.inf.datum.AuthService.filter;

import antlr.Token;
import br.inf.datum.AuthService.entity.User;
import br.inf.datum.AuthService.exception.MessageException;
import br.inf.datum.AuthService.repository.UserRepository;
import br.inf.datum.AuthService.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class AuthenticationFilter extends GenericFilterBean {



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        final String requestTokenHeader =((HttpServletRequest)  request).getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            username = TokenAuthenticationService.getUsernameFromToken(jwtToken);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (TokenAuthenticationService.validateTokenDte(jwtToken)) {
                Authentication authentication = TokenAuthenticationService
                .getAuthentication((HttpServletRequest) request,response);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                String json = String.format("{\"message\": \"%s\"}", "Sessão inválida");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,json);
            }
        }
        filterChain.doFilter(request, response);
    }
}