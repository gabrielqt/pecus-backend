package gabrielqt.pecus.service;

import gabrielqt.pecus.dto.request.FarmRequest;
import gabrielqt.pecus.dto.response.FarmResponse;
import gabrielqt.pecus.entity.Farm;
import gabrielqt.pecus.entity.User;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.mapper.FarmMapper;
import gabrielqt.pecus.repository.FarmRepository;
import gabrielqt.pecus.validator.FarmValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FarmService {
    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;
    private final FarmValidator farmValidator;

    public FarmResponse save(FarmRequest farmRequest, User user) {

        Farm farm = farmValidator.validateFarmExistsAndReturnFarm(farmRequest);
        farmValidator.validateOwner(farm, user);
        return farmMapper.toResponse(farmRepository.save(farmMapper.toEntity(farmRequest, user)));
    }

    public Farm findById(Long id) {

        return farmRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Farm.class, id));
    }

    public FarmResponse findById(Long id, User user) {

        Farm farm = findById(id);
        return farmMapper.toResponse(farm);
    }

    public Page<FarmResponse> findByUser(Pageable pageable, User user) {

        return farmRepository.findAllByUser(pageable, user.getId()).map(farmMapper::toResponse);
    }
}
