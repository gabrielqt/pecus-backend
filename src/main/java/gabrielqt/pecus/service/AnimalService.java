package gabrielqt.pecus.service;

import static java.util.Objects.nonNull;

import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gabrielqt.pecus.dto.request.AnimalRequest;
import gabrielqt.pecus.dto.response.AnimalResponse;
import gabrielqt.pecus.entity.Animal;
import gabrielqt.pecus.entity.Breed;
import gabrielqt.pecus.entity.Farm;
import gabrielqt.pecus.entity.Lot;
import gabrielqt.pecus.entity.User;
import gabrielqt.pecus.exception.BusinessException;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.mapper.AnimalMapper;
import gabrielqt.pecus.repository.AnimalRepository;
import gabrielqt.pecus.repository.BreedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final FarmService farmService;
    private final LotService lotService;
    private final BreedRepository breedRepository;
    private final AnimalWeighingService animalWeighingService;

    @Transactional
    public AnimalResponse save(AnimalRequest animalRequest, User user) {

        validateSave(animalRequest);
        
        Animal animal = buildAndSaveAnimal(animalRequest);

        saveInitialAnimalWeighingIfPresent(animalRequest, animal);

        return animalMapper.toResponse(animal);
    }

    public Animal findById(Long id) {

        return animalRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Animal.class, id));
    }

    public Page<AnimalResponse> findAllByFarmId(Long farmId, Pageable pageable) {

        return animalRepository.findByFarmId(farmId, pageable).map(animalMapper::toResponse);
    }

    private Breed findBreedById(Long breedId) {

        return breedRepository.findById(breedId)
                .orElseThrow(() -> new ObjectNotFoundException(Breed.class, breedId));
    }

    private void validateAnimalExists(AnimalRequest animalRequest) {

        if (nonNull(animalRequest.id())) {

            findById(animalRequest.id());
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

    private Animal buildAndSaveAnimal(AnimalRequest animalRequest) {

        Farm farm = farmService.findById(animalRequest.farmId());
        Lot lot = nonNull(animalRequest.lotId()) ? lotService.findById(animalRequest.lotId()) : null;
        Breed breed = nonNull(animalRequest.breedId()) ? findBreedById(animalRequest.breedId()) : null;

        return animalRepository.save(animalMapper.toEntity(animalRequest, farm, lot, breed));
    }

    private void validateSave(AnimalRequest animalRequest) {

        validateAnimalExists(animalRequest);
        validateExistsEartag(animalRequest.farmId(), animalRequest.earTag());
        validateLotAndFarm(animalRequest.farmId(), animalRequest.lotId());
    }

    private void saveInitialAnimalWeighingIfPresent(AnimalRequest animalRequest, Animal animal) {

        if (nonNull(animalRequest.animalWeighingRequest())) {

            animalWeighingService.save(animalRequest.animalWeighingRequest(), animal);
        }
    }

}
