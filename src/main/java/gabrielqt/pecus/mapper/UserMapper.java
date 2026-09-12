package gabrielqt.pecus.mapper;

import gabrielqt.pecus.dto.request.RegisterRequest;
import gabrielqt.pecus.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User toEntity(RegisterRequest registerRequest) {
        return User.builder()
                .nickname(registerRequest.nickname())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role(registerRequest.role())
                .build();
    }
}
