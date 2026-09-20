package gabrielqt.pecus.service;

import gabrielqt.pecus.dto.request.LotRequest;
import gabrielqt.pecus.dto.response.LotResponse;
import gabrielqt.pecus.entity.Farm;
import gabrielqt.pecus.entity.Lot;
import gabrielqt.pecus.entity.User;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.mapper.LotMapper;
import gabrielqt.pecus.repository.LotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LotService {
    private final LotRepository lotRepository;
    private final FarmService farmService;
    private final LotMapper lotMapper;

    public LotResponse save(LotRequest request, User user){

        Farm farm = farmService.findById(request.farmId());
        validateLotExists(request.id());
        return lotMapper.toResponse(lotRepository.save(lotMapper.toEntity(request, farm)));
    }

    public Lot findById(Long id){

        return lotRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Lot.class, id));
    }


    public Page<LotResponse> findAllByFarmId(Long farmId, Pageable pageable){
        return lotRepository.findByFarmId(farmId, pageable).map(lotMapper::toResponse);
    }

    private void validateLotExists(Long id){

        if (id != null){
            findById(id);
        }
    }

    public boolean existsLotInFarm(Long farmId, Long lotId){

        return lotRepository.existsByFarmIdAndId(farmId, lotId);
    }

}
