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
@Table(name = "animal_weighing")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalWeighing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Column(nullable = false)
    private BigDecimal weight;

    @NotNull
    private LocalDateTime weighingDate;
}
