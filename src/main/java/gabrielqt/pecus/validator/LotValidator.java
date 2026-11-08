package gabrielqt.pecus.validator;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import gabrielqt.pecus.dto.request.LotRequest;
import gabrielqt.pecus.entity.Lot;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.repository.LotRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LotValidator {

    // repository e não LotService: LotService depende desse validator, daria dependência circular
    private final LotRepository lotRepository;

    public void validateLotExistsAndFarm(LotRequest request){

        if (request.id() != null){

            Lot lot = lotRepository.findById(request.id())
                    .orElseThrow(() -> new ObjectNotFoundException(Lot.class, request.id()));

            if (!lot.getFarm().getId().equals(request.farmId())) {

                throw new AccessDeniedException("Esse lote não pertence a essa fazenda.");
            }
        }
    }
}
