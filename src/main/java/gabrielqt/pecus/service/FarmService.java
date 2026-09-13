package gabrielqt.pecus.service;

import gabrielqt.pecus.dto.request.FarmRequest;
import gabrielqt.pecus.dto.response.FarmResponse;
import gabrielqt.pecus.entity.Farm;
import gabrielqt.pecus.entity.User;
import gabrielqt.pecus.exception.BusinessException;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.mapper.FarmMapper;
import gabrielqt.pecus.repository.FarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class FarmService {
    private FarmRepository farmRepository;
    private FarmMapper farmMapper;

    public FarmResponse save(FarmRequest farmRequest, User user) {
        if (!isNull(farmRequest.id())) {
            Farm farm = findById(farmRequest.id());
        }
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
