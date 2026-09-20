package gabrielqt.pecus.mapper;

import org.springframework.stereotype.Component;

import gabrielqt.pecus.dto.request.AnimalWeighingRequest;
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
}
