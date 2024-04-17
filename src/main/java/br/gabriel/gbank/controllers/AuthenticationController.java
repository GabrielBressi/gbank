package br.gabriel.gbank.controllers;

import br.gabriel.gbank.dtos.AuthDTO;
import br.gabriel.gbank.dtos.LoginResponseDTO;
import br.gabriel.gbank.dtos.UserDTO;
import br.gabriel.gbank.infra.security.TokenService;
import br.gabriel.gbank.models.Account;
import br.gabriel.gbank.models.User;
import br.gabriel.gbank.repositories.AccountRepository;
import br.gabriel.gbank.repositories.UserRepository;
import br.gabriel.gbank.services.PasswordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO data) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserDTO data) {
        if (this.userRepository.findUserByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        Account newAccount = new Account(
                data.account().accountNumber(),
                data.account().agency(),
                BigDecimal.ZERO
        );
        this.accountRepository.save(newAccount);

        User newUser = new User(
                data.firstname(),
                data.lastname(),
                data.cpf(),
                data.email(),
                this.passwordService.encryptPassword(data.password()),
                this.accountRepository.findById(newAccount.getId())
        );

        this.userRepository.save(newUser);

        return new ResponseEntity(newUser, HttpStatus.CREATED);
    }
}
