package gabrielqt.pecus.dto.request;

import gabrielqt.pecus.entity.enums.UF;
import jakarta.validation.constraints.NotNull;

public record FarmRequest(
        Long id,
        @NotNull String name,
        String city,
        UF state
) {
}
