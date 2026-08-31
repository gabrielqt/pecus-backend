package gabrielqt.pecus.entity;

import gabrielqt.pecus.entity.enums.FinancialMovementCategory;
import gabrielqt.pecus.entity.enums.FinancialMovementType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_movement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(0)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @Size(max=100)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    @Nullable
    private Animal animal;

    @Enumerated(EnumType.STRING)
    @NotNull
    private FinancialMovementType financialMovementType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private FinancialMovementCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    @Nullable
    private Lot lot;

    @NotNull
    private LocalDateTime transactionDateTime;
}
