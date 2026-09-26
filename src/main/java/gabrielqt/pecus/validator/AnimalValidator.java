package gabrielqt.pecus.validator;

import static java.util.Objects.nonNull;

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

        validateAnimalExists(animalRequest);
        validateExistsEartag(animalRequest.farmId(), animalRequest.earTag());
        validateLotAndFarm(animalRequest.farmId(), animalRequest.lotId());
    }

    private void validateAnimalExists(AnimalRequest animalRequest) {

        if (nonNull(animalRequest.id())) {

            animalRepository.findById(animalRequest.id())
                    .orElseThrow(() -> new ObjectNotFoundException(Animal.class, animalRequest.id()));
        }
    }

    private void validateExistsEartag(Long farmId, String eartag){

        if (animalRepository.existsByFarmIdAndEartag(farmId,  eartag)) {

            throw new BusinessException("Número de brinco já cadastrado nessa fazenda.");
        }
    }

    private void validateLotAndFarm(Long farmId, Long lotId) {

        if (nonNull(lotId) && !lotService.existsLotInFarm(farmId, lotId)){

            throw new BusinessException("Esse lote não pertence a essa fazenda.");
        }
    }
}
