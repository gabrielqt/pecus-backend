package gabrielqt.pecus.validator;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import gabrielqt.pecus.dto.request.AnimalRequest;
import gabrielqt.pecus.entity.Animal;
import gabrielqt.pecus.exception.BusinessException;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.repository.AnimalRepository;
import gabrielqt.pecus.service.LotService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AnimalValidator {

    private final AnimalRepository animalRepository;
    private final LotService lotService;

    public void validateSave(AnimalRequest animalRequest) {

        validateAnimalExistsAndFarm(animalRequest);
        validateExistsEartag(animalRequest.farmId(), animalRequest.earTag(), animalRequest.id());
        validateLotAndFarm(animalRequest.farmId(), animalRequest.lotId());
    }

    private void validateAnimalExistsAndFarm(AnimalRequest animalRequest) {

        if (nonNull(animalRequest.id())) {

            Animal animal = animalRepository.findById(animalRequest.id())
                    .orElseThrow(() -> new ObjectNotFoundException(Animal.class, animalRequest.id()));

            if (!animal.getFarm().getId().equals(animalRequest.farmId())) {

                throw new AccessDeniedException("Esse animal não pertence a essa fazenda.");
            }
        }
    }

    private void validateExistsEartag(Long farmId, String eartag, Long animalId){

        if ((isNull(animalId) && animalRepository.existsByFarmIdAndEartag(farmId,  eartag))
                ||
          (nonNull(animalId) && animalRepository.existsByFarmIdAndEartagAndIdNot(farmId, eartag, animalId))
        ) {

            throw new BusinessException("Número de brinco já cadastrado nessa fazenda.");
        }
    }

    private void validateLotAndFarm(Long farmId, Long lotId) {

        if (nonNull(lotId) && !lotService.existsLotInFarm(farmId, lotId)){

            throw new BusinessException("Esse lote não pertence a essa fazenda.");
        }
    }
}
