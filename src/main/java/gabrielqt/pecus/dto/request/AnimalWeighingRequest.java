package gabrielqt.pecus.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AnimalWeighingRequest(
    @NotNull(message = "Animal não informado.")
    Long animalId,

    @NotNull(message = "Peso não informado.")
    @Positive(message = "O peso deve ser maior que zero.")
    BigDecimal weight,

    @NotNull(message = "A data da pesagem é obrigatória.")
    LocalDate weighingDate
) {

}
