package gabrielqt.pecus.mapper;

import gabrielqt.pecus.dto.request.LotRequest;
import gabrielqt.pecus.dto.response.LotResponse;
import gabrielqt.pecus.entity.Farm;
import gabrielqt.pecus.entity.Lot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LotMapper {
    public Lot toEntity(LotRequest lotRequest, Farm farm) {
        return Lot.builder()
                .id(lotRequest.id())
                .name(lotRequest.name())
                .paddock(lotRequest.paddock())
                .farm(farm)
                .build();

    }

    public LotResponse toResponse(Lot lot) {
        return new LotResponse(lot.getId(), lot.getName(), lot.getPaddock(), lot.getFarm().getId());
    }
}
