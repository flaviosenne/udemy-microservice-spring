package com.microservice.auth.controller;

import com.microservice.auth.entity.User;
import com.microservice.auth.jwt.JwtTokenProvider;
import com.microservice.auth.repository.UserRepository;
import com.microservice.auth.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @RequestMapping("/testSecurity")
    public String test(){
        return "testado";
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
    consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> login(@RequestBody UserVO userVO){
        try {

            String userName = userVO.getUserName();
            String password = userVO.getPassword();

            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

            User user = userRepository.findByUserName(userName)
                    .orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado"));

            String token = this.jwtTokenProvider.createToken(userName, user.getPermissions());

            Map<Object, Object> model = new HashMap<>();
            model.put("username", userName);
            model.put("token", token);

            return ResponseEntity.ok(model);

        }catch (AuthenticationException e){
            throw new BadCredentialsException("Credenciais inválidas");
        }
    }
}
