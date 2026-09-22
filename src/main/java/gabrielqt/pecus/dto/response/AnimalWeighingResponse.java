package gabrielqt.pecus.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AnimalWeighingResponse(
    Long id,
    Long animalId,
    BigDecimal weight,
    LocalDate weighingDate
) {
}
