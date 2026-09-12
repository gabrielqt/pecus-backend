package gabrielqt.pecus.dto.request;

import gabrielqt.pecus.entity.enums.Role;

public record RegisterRequest(
        String email,
        String nickname,
        String password,
        Role role
) {
}
