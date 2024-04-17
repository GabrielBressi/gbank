package br.gabriel.gbank.dtos;

public record UserDTO(String firstname, String lastname, String cpf, String email, String password, AccountDTO account) {
}
