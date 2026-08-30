package gabrielqt.pecus.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lot_transfer_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LotTransferHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_lot_id")
    private Lot newLot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_lot_id")
    private Lot oldLot;

    @NotNull
    private LocalDateTime transferDate;
}
