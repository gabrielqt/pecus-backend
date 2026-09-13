package gabrielqt.pecus.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LotRequest(
        Long id,
        @NotBlank String name,
        @NotBlank String paddock,
        Long farmId
) {
}
