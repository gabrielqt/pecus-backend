package gabrielqt.pecus.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import gabrielqt.pecus.entity.Animal;
import gabrielqt.pecus.entity.AnimalWeighing;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.repository.AnimalRepository;
import gabrielqt.pecus.repository.AnimalWeighingRepository;

import lombok.RequiredArgsConstructor;

// o animal não tem dono próprio: quem manda nele é o dono/funcionário da fazenda, então delega pro FarmSecurity
@Component("animalSecurity")
@RequiredArgsConstructor
public class AnimalSecurity {

    private final AnimalRepository animalRepository;
    private final AnimalWeighingRepository animalWeighingRepository;
    private final FarmSecurity farmSecurity;

    public boolean isOwner(Long animalId, Authentication authentication) {

        return farmSecurity.isOwner(getAnimal(animalId).getFarm().getId(), authentication);
    }

    public boolean canAccess(Long animalId, Authentication authentication) {

        return farmSecurity.canAccess(getAnimal(animalId).getFarm().getId(), authentication);
    }

    public boolean canAccessWeighing(Long weighingId, Authentication authentication) {

        AnimalWeighing weighing = animalWeighingRepository.findById(weighingId)
                .orElseThrow(() -> new ObjectNotFoundException(AnimalWeighing.class, weighingId));

        return farmSecurity.canAccess(weighing.getAnimal().getFarm().getId(), authentication);
    }

    private Animal getAnimal(Long animalId) {

        return animalRepository.findById(animalId)
                .orElseThrow(() -> new ObjectNotFoundException(Animal.class, animalId));
    }
}
