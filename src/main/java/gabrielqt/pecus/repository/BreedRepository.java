package gabrielqt.pecus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gabrielqt.pecus.entity.Breed;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {
}
