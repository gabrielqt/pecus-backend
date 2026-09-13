package gabrielqt.pecus.security;

import gabrielqt.pecus.entity.Farm;
import gabrielqt.pecus.entity.User;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.repository.FarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("farmSecurity")
@RequiredArgsConstructor
public class FarmSecurity {

    private final FarmRepository farmRepository;

    public boolean canAccess(Long farmId, Authentication authentication) {

        Farm farm = getFarm(farmId);
        Long userId = getUserId(authentication);

        return isOwner(farm, userId) || isWorker(farm, userId);
    }

    public boolean isOwner(Long farmId, Authentication authentication){
        Farm farm = getFarm(farmId);
        Long userId = getUserId(authentication);

        return isOwner(farm, userId);
    }

    public boolean isWorker(Long farmId, Authentication authentication){
        Farm farm = getFarm(farmId);
        Long userId = getUserId(authentication);

        return isWorker(farm, userId);
    }

    private Long getUserId(Authentication authentication) {
        return  ((User) authentication.getPrincipal()).getId();
    }

    private Farm getFarm(Long farmId) {
        return farmRepository.findById(farmId).orElseThrow(() -> new ObjectNotFoundException(Farm.class,  farmId));
    }

    private boolean isOwner(Farm farm, Long userId) {
        return farm.getOwner().getId().equals(userId);
    }

    private boolean isWorker(Farm farm, Long userId) {
        return farm.getWorkers().stream().anyMatch(worker -> worker.getId().equals(userId));
    }
}

