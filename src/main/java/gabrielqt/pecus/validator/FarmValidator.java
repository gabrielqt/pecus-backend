package gabrielqt.pecus.validator;

import org.springframework.security.access.AccessDeniedException;
import static java.util.Objects.nonNull;

import org.springframework.stereotype.Component;

import gabrielqt.pecus.dto.request.FarmRequest;
import gabrielqt.pecus.entity.Farm;
import gabrielqt.pecus.entity.User;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.repository.FarmRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FarmValidator {

    private final FarmRepository farmRepository;

    public Farm validateFarmExistsAndReturnFarm(FarmRequest farmRequest) {

        if (nonNull(farmRequest.id())) {
            return farmRepository.findById(farmRequest.id())
                    .orElseThrow(() -> new ObjectNotFoundException(Farm.class, farmRequest.id()));
        }
        return null;
    }

    public void validateOwner(Farm farm, User user) {

        if (nonNull(farm) && !farm.getOwner().getId().equals(user.getId())) {

            throw new AccessDeniedException("Somente o dono pode alterar essa fazenda.");
        }
    }
}
