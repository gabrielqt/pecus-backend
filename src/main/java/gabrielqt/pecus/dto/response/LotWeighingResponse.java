package gabrielqt.pecus.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LotWeighingResponse (
        Long id,
        Long lotId,
        LocalDate weighingDate,
        Integer sampledAnimalsCount,
        BigDecimal averageSampledWeight
){
}
