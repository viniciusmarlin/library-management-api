package br.com.viniciusmarlin.library.service;

import br.com.viniciusmarlin.library.dto.UserDTO;
import br.com.viniciusmarlin.library.model.UserModel;
import br.com.viniciusmarlin.library.repository.IUserRepository;
import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class UserService {

    // Injeção de dependência do IUserRepository
    private final IUserRepository userRepository;

    // Construtor para injetar o IUserRepository
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Método para registrar um novo usuário
    public UserModel register(UserDTO.RegisterUserDTO dto) {

        // Verificar se o email já está registrado
        UserModel user = new UserModel();
        user.setName(dto.name());
        user.setEmail(dto.email());

        // Hash da senha usando BCrypt
        String hashedPassword = BCrypt.withDefaults()
                .hashToString(12, dto.password().toCharArray());

        user.setPassword(hashedPassword); // Armazenar a senha encriptada
        return userRepository.save(user); // Salvar o usuário no banco de dados
    }

    // Método para login de usuário
    public UserModel login(String email, String password) {

        // Buscar o usuário pelo email
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        var result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()); // Verificar a senha usando BCrypt

        // Se a senha não for válida, lançar uma exceção
        if(!result.verified) {
            throw new RuntimeException("Senha incorreta");
        }
        return user; // Se a senha for válida, retornar o usuário
    }
}
