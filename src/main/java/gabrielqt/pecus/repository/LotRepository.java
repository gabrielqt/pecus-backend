package gabrielqt.pecus.repository;

import gabrielqt.pecus.entity.Lot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotRepository extends JpaRepository<Lot, Long> {
    Page<Lot>  findByFarmId(Long farmId, Pageable pageable);
    boolean existsByFarmIdAndId(Long farmId,  Long lotId);
}
