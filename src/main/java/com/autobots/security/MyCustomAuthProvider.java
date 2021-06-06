package com.autobots.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.spec.PSource;
import java.util.Arrays;
import java.util.Collection;

@Component
public class MyCustomAuthProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        System.out.println("Inside MyCustomAuthProvider#authenticate...");

        String username = authentication.getName();
        System.out.println("username: " + username);

        String password = authentication.getCredentials().toString();
        System.out.println("credentials: " + password);

        String principal = authentication.getPrincipal().toString();
        System.out.println("principal: " + principal);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        System.out.println("print authorities:");
        Arrays.asList(authorities).forEach(auth -> {
            System.out.println(auth);
        });

        if (username.equals("tom") && password.equals("password")) {
            return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList());
        }

        throw new BadCredentialsException("username OR password not correct");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        StackTraceElement [] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraceElements) {
            System.out.println(element.getClassName() + " method: " + element.getMethodName());

        }

        System.out.println("Inside MyCustomAuthenticationProiver#supports");
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
