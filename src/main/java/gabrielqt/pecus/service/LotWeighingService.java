package gabrielqt.pecus.service;

import org.springframework.stereotype.Service;

import gabrielqt.pecus.dto.request.LotWeighingRequest;
import gabrielqt.pecus.entity.Lot;
import gabrielqt.pecus.mapper.LotWeighingMapper;
import gabrielqt.pecus.repository.LotWeighingRepository;
import gabrielqt.pecus.validator.LotWeighingValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LotWeighingService {

    private final LotWeighingRepository lotWeighingRepository;
    private final LotWeighingMapper lotWeighingMapper;
    private final LotService lotService;
    private final LotWeighingValidator lotWeighingValidator;

    public void save(LotWeighingRequest request) {

        Lot lot = validateLotExistsAndReturnLot(request);
        lotWeighingValidator.validateSave(request, lot);

        lotWeighingRepository.save(lotWeighingMapper.toEntity(request, lot));
    }

    private Lot validateLotExistsAndReturnLot(LotWeighingRequest request) {

        return lotService.findById(request.lotId());
    }

}
