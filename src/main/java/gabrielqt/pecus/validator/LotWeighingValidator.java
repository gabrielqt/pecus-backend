package gabrielqt.pecus.validator;

import org.springframework.stereotype.Component;

import gabrielqt.pecus.dto.request.LotWeighingRequest;
import gabrielqt.pecus.entity.Lot;
import gabrielqt.pecus.exception.BusinessException;
import gabrielqt.pecus.repository.LotWeighingRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LotWeighingValidator {

    private final LotWeighingRepository lotWeighingRepository;

    public void validateSave(LotWeighingRequest request, Lot lot) {

        validateLotWeighingDoesNotExistOnDate(request);
        validateSampledAnimalsCountDoesNotExceedLotAnimals(request, lot);
    }

    private void validateLotWeighingDoesNotExistOnDate(LotWeighingRequest request) {

        if (lotWeighingRepository.existsByLotIdAndWeighingDate(request.lotId(), request.weighingDate())) {

            throw new BusinessException("Lote já possui uma pesagem registrada nesta data.");
        }
    }

    private void validateSampledAnimalsCountDoesNotExceedLotAnimals(LotWeighingRequest request, Lot lot) {

        if (request.sampledAnimalsCount() > lot.getAnimals().size()) {

            throw new BusinessException("Número de animais usados na amostragem é superior aos animais no lote.");
        }
    }
}
