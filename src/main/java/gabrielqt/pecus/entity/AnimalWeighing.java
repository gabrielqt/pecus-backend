package gabrielqt.pecus.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    @NotNull
    private Animal animal;

    @Column(nullable = false)
    @Min(value = 0, message = "Não é possível gravar um animal com peso 0.")
    private BigDecimal weight;

    @NotNull
    private LocalDate weighingDate;
}
