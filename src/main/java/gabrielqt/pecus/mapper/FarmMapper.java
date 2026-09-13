package gabrielqt.pecus.mapper;

import gabrielqt.pecus.dto.request.FarmRequest;
import gabrielqt.pecus.dto.response.FarmResponse;
import gabrielqt.pecus.entity.Farm;
import gabrielqt.pecus.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FarmMapper{

    public Farm toEntity(FarmRequest farmRequest, User user){
        return Farm.builder()
                .id(farmRequest.id())
                .name(farmRequest.name())
                .owner(user)
                .city(farmRequest.city())
                .state(farmRequest.state())
                .build();
    }

    public FarmResponse toResponse(Farm farm){
        return new FarmResponse(
                farm.getId(),
                farm.getName(),
                farm.getCity(),
                farm.getState()
        );
    }
}
