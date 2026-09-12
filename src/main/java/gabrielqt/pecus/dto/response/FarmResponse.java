package gabrielqt.pecus.dto.response;

import gabrielqt.pecus.entity.enums.UF;

public record FarmResponse(
        Long id,
        String name,
        String city,
        UF state
) {
}
