package gabrielqt.pecus.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import gabrielqt.pecus.entity.AnimalWeighing;

public interface AnimalWeighingRepository extends JpaRepository<AnimalWeighing, Long> {
    Page<AnimalWeighing> findByAnimalId(Long animalId, Pageable pageable);
    boolean existsByAnimalIdAndWeighingDate(Long animalId, LocalDate weighingDate);
    boolean existsByAnimalIdAndWeighingDateAndIdNot(Long animalId, LocalDate weighingDate, Long id);
}
