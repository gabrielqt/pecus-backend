package gabrielqt.pecus.dto.request;

import gabrielqt.pecus.entity.enums.Role;

public record RegisterRequest(
        String email,
        String password,
        Role role
) {
}
