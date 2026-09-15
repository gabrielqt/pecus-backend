package gabrielqt.pecus.entity;

import gabrielqt.pecus.entity.enums.UF;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "farm")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String city;

    @Enumerated(EnumType.STRING)
    private UF state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "farm_worker",
            joinColumns = @JoinColumn(name = "farm_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> workers;
}
