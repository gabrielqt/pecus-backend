package gabrielqt.pecus.entity;

import gabrielqt.pecus.entity.enums.AnimalCategory;
import gabrielqt.pecus.entity.enums.Sex;
import gabrielqt.pecus.entity.enums.StatusAnimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "animal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String earTag;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAnimal status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sex sex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id")
    private Breed breed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @Column(nullable = false)
    private LocalDate birthDate;

    public AnimalCategory getCategory(){
        long daysLife = ChronoUnit.DAYS.between(birthDate, LocalDate.now());
        if(daysLife <= 240){
            return AnimalCategory.CALF;
        }
        else if(daysLife <= 720){
            return AnimalCategory.YEARLING;
        }
        else{
            return AnimalCategory.ADULT;
        }
    }
}
