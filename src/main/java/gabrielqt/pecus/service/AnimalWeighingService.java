package gabrielqt.pecus.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import gabrielqt.pecus.dto.request.AnimalWeighingRequest;
import gabrielqt.pecus.entity.Animal;
import gabrielqt.pecus.exception.BusinessException;
import gabrielqt.pecus.mapper.AnimalWeighingMapper;
import gabrielqt.pecus.repository.AnimalWeighingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalWeighingService {

    private final AnimalWeighingRepository animalWeighingRepository;
    private final AnimalWeighingMapper animalWeighingMapper;

    public void save(AnimalWeighingRequest request, Animal animal) {

        validateAnimalWeighingDoesNotExist(request.animalId(), request.weighingDate());

        animalWeighingRepository.save(animalWeighingMapper.toEntity(request, animal));
    }

    private void validateAnimalWeighingDoesNotExist(Long animalId, LocalDate date) {

        if (animalWeighingRepository.existsByAnimalIdAndDate(animalId, date)) {

            throw new BusinessException("Animal já possui uma pesagem registrada nesta data.");
        }
    }
}
