package gabrielqt.pecus.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lot_weighing")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LotWeighing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @NotNull
    private LocalDateTime weighingDate;

    @NotNull
    private Integer sampledAnimalsCount;

    @NotNull
    private BigDecimal totalSampledWeight;

    public BigDecimal getAverageSampledWeight() {
        if (sampledAnimalsCount == null || sampledAnimalsCount == 0) {return BigDecimal.ZERO; }
        return totalSampledWeight.divide(
                BigDecimal.valueOf(sampledAnimalsCount), 2, BigDecimal.ROUND_HALF_UP
        );
    }
}
