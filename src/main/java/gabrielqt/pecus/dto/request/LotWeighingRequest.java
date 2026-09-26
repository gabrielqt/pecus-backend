package gabrielqt.pecus.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record LotWeighingRequest(
        @NotNull Long lotId,
        @NotNull LocalDate weighingDate,
        @NotNull Integer sampledAnimalsCount,
        @NotNull BigDecimal totalSampledWeight
){
}
