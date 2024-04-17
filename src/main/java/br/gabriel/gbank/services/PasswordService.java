package br.gabriel.gbank.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    public String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

}
