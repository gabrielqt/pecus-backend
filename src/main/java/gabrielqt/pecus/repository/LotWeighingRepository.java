package gabrielqt.pecus.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import gabrielqt.pecus.entity.LotWeighing;

public interface LotWeighingRepository  extends JpaRepository<LotWeighing, Long> {
    boolean existsByLotIdAndWeighingDate(Long lotId, LocalDate weighingDate);
}
