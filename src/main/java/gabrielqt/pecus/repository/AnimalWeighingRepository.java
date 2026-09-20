package gabrielqt.pecus.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import gabrielqt.pecus.entity.AnimalWeighing;

public interface AnimalWeighingRepository extends JpaRepository<AnimalWeighing, Long> {
    boolean existsByAnimalIdAndDate(Long animalId, LocalDate date);
}
