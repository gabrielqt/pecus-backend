package gabrielqt.pecus.mapper;

import org.springframework.stereotype.Component;

import gabrielqt.pecus.dto.request.AnimalWeighingRequest;
import gabrielqt.pecus.dto.response.AnimalWeighingResponse;
import gabrielqt.pecus.entity.Animal;
import gabrielqt.pecus.entity.AnimalWeighing;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AnimalWeighingMapper {

    public AnimalWeighing toEntity(AnimalWeighingRequest request, Animal animal) {
        return AnimalWeighing.builder()
                .animal(animal)
                .weighingDate(request.weighingDate())
                .weight(request.weight())
                .build();
    }

    public AnimalWeighingResponse toResponse(AnimalWeighing animalWeighing) {
        return new AnimalWeighingResponse(
                animalWeighing.getId(),
                animalWeighing.getAnimal().getId(),
                animalWeighing.getWeight(),
                animalWeighing.getWeighingDate()
        );
    }
}
