package br.com.viniciusmarlin.library.service;

import br.com.viniciusmarlin.library.dto.UserDTO;
import br.com.viniciusmarlin.library.model.UserModel;
import br.com.viniciusmarlin.library.repository.IUserRepository;
import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel register(UserDTO.RegisterUserDTO dto) {

        UserModel user = new UserModel();
        user.setName(dto.name());
        user.setEmail(dto.email());

        String hashedPassword = BCrypt.withDefaults()
                .hashToString(12, dto.password().toCharArray());

        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public UserModel login(String email, String password) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

        if(!result.verified) {
            throw new RuntimeException("Senha incorreta");
        }

        return user;
    }
}
