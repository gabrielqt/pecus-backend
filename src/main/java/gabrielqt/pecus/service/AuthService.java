package gabrielqt.pecus.service;

import gabrielqt.pecus.dto.request.LoginRequest;
import gabrielqt.pecus.dto.request.RegisterRequest;
import gabrielqt.pecus.dto.response.TokenResponse;
import gabrielqt.pecus.entity.User;
import gabrielqt.pecus.exception.EmailAlreadyRegisteredException;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.mapper.UserMapper;
import gabrielqt.pecus.repository.UserRepository;
import gabrielqt.pecus.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public TokenResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()  )
        );


        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        String token = jwtService.generateToken(user);

        return new TokenResponse(token);
    }

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyRegisteredException(request.email());
        }
        User user = userRepository.save(userMapper.toEntity(request));
    }
}
