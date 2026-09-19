package gabrielqt.pecus.mapper;

import static java.util.Objects.isNull;

import org.springframework.stereotype.Component;

import gabrielqt.pecus.dto.request.AnimalRequest;
import gabrielqt.pecus.dto.response.AnimalResponse;
import gabrielqt.pecus.entity.Animal;
import gabrielqt.pecus.entity.Breed;
import gabrielqt.pecus.entity.Farm;
import gabrielqt.pecus.entity.Lot;
import gabrielqt.pecus.entity.enums.StatusAnimal;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AnimalMapper {

    public Animal toEntity(AnimalRequest animalRequest, Farm farm, Lot lot, Breed breed) {
        return Animal.builder()
                .id(animalRequest.id())
                .earTag(animalRequest.earTag())
                .status(isNull(animalRequest.status()) ? StatusAnimal.ACTIVE : animalRequest.status())
                .sex(animalRequest.sex())
                .breed(breed)
                .farm(farm)
                .lot(lot)
                .birthDate(animalRequest.birthDate())
                .build();
    }

    public AnimalResponse toResponse(Animal animal) {
        return new AnimalResponse(
                animal.getId(),
                animal.getEarTag(),
                animal.getStatus(),
                animal.getSex(),
                isNull(animal.getBreed()) ? null : animal.getBreed().getId(),
                animal.getFarm().getId(),
                isNull(animal.getLot()) ? null : animal.getLot().getId(),
                animal.getBirthDate(),
                animal.getCategory()
        );
    }
}
