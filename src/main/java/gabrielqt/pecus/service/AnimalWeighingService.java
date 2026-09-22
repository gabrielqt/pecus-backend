package gabrielqt.pecus.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gabrielqt.pecus.dto.request.AnimalWeighingRequest;
import gabrielqt.pecus.dto.response.AnimalWeighingResponse;
import gabrielqt.pecus.entity.Animal;
import gabrielqt.pecus.entity.AnimalWeighing;
import gabrielqt.pecus.exception.BusinessException;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.mapper.AnimalWeighingMapper;
import gabrielqt.pecus.repository.AnimalRepository;
import gabrielqt.pecus.repository.AnimalWeighingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalWeighingService {

    private final AnimalWeighingRepository animalWeighingRepository;
    private final AnimalWeighingMapper animalWeighingMapper;

    // repository e não AnimalService: AnimalService já depende desse service (peso inicial), daria dependência circular
    private final AnimalRepository animalRepository;

    @Transactional
    public AnimalWeighingResponse save(AnimalWeighingRequest request) {

        return save(request, findAnimalById(request.animalId()));
    }

    // usado direto pelo AnimalService, nesse fluxo request.animalId() é null, o animal vem já salvo
    @Transactional
    public AnimalWeighingResponse save(AnimalWeighingRequest request, Animal animal) {

        validateSave(request, animal);

        return animalWeighingMapper.toResponse(animalWeighingRepository.save(animalWeighingMapper.toEntity(request, animal)));
    }

    public void deleteById(Long animalWeighingId) {

        AnimalWeighing animalWeighing = findById(animalWeighingId);

        animalWeighingRepository.delete(animalWeighing);
    }

    public AnimalWeighing findById(Long id) {

        return animalWeighingRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(AnimalWeighing.class, id));
    }

    public AnimalWeighingResponse findResponseById(Long id) {

        return animalWeighingMapper.toResponse(findById(id));
    }

    public Page<AnimalWeighingResponse> findAllByAnimalId(Long animalId, Pageable pageable) {

        return animalWeighingRepository.findByAnimalId(animalId, pageable).map(animalWeighingMapper::toResponse);
    }

    private Animal findAnimalById(Long animalId) {

        return animalRepository.findById(animalId)
                .orElseThrow(() -> new ObjectNotFoundException(Animal.class, animalId));
    }

    private void validateSave(AnimalWeighingRequest request, Animal animal) {

        validateAnimalWeighingDoesNotExistOnDate(animal.getId(), request.weighingDate());
    }

    private void validateAnimalWeighingDoesNotExistOnDate(Long animalId, LocalDate date) {

         if (animalWeighingRepository.existsByAnimalIdAndWeighingDate(animalId, date)) {

             throw new BusinessException("Animal já possui uma pesagem registrada nesta data.");
         }
    }
}
