package gabrielqt.pecus.validator;

import static java.util.Objects.isNull;

import org.springframework.stereotype.Component;

import gabrielqt.pecus.dto.request.FarmRequest;
import gabrielqt.pecus.entity.Farm;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.repository.FarmRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FarmValidator {

    // repository e não FarmService: FarmService depende desse validator, daria dependência circular
    private final FarmRepository farmRepository;

    public void validateFarmExists(FarmRequest farmRequest) {

        if (!isNull(farmRequest.id())) {
            farmRepository.findById(farmRequest.id())
                    .orElseThrow(() -> new ObjectNotFoundException(Farm.class, farmRequest.id()));
        }
    }
}
