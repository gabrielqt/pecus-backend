package gabrielqt.pecus.repository;

import gabrielqt.pecus.entity.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

    @Query("""
    SELECT DISTINCT f
    FROM Farm f
    LEFT JOIN f.workers w
    WHERE f.owner.id = :userId
       OR w.id = :userId
    """)
    Page<Farm> findAllByUser(Pageable pageable, Long userId);

}
