package gabrielqt.pecus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gabrielqt.pecus.entity.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Page<Animal> findByFarmId(Long farmId, Pageable pageable);
}
