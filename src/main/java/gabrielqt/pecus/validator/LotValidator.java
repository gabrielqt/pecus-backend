package gabrielqt.pecus.validator;

import org.springframework.stereotype.Component;

import gabrielqt.pecus.entity.Lot;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.repository.LotRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LotValidator {

    // repository e não LotService: LotService depende desse validator, daria dependência circular
    private final LotRepository lotRepository;

    public void validateLotExists(Long id){

        if (id != null){
            lotRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException(Lot.class, id));
        }
    }
}
