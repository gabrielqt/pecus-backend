package gabrielqt.pecus.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email não informado.")
        String email,

        @NotBlank(message = "Senha não informada.")
        String password
) {
}
