package gabrielqt.pecus.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AnimalWeighingRequest(
    Long animalId,
    BigDecimal weight,
    LocalDate weighingDate
) {

}
