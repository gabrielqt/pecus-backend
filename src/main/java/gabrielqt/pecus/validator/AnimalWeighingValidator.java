package gabrielqt.pecus.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import gabrielqt.pecus.dto.request.AnimalWeighingRequest;
import gabrielqt.pecus.entity.Animal;
import gabrielqt.pecus.exception.BusinessException;
import gabrielqt.pecus.repository.AnimalWeighingRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AnimalWeighingValidator {

    private final AnimalWeighingRepository animalWeighingRepository;

    public void validateSave(AnimalWeighingRequest request, Animal animal) {

        validateAnimalWeighingDoesNotExistOnDate(animal.getId(), request.weighingDate());
    }

    private void validateAnimalWeighingDoesNotExistOnDate(Long animalId, LocalDate date) {

         if (animalWeighingRepository.existsByAnimalIdAndWeighingDate(animalId, date)) {

             throw new BusinessException("Animal já possui uma pesagem registrada nesta data.");
         }
    }
}
