package gabrielqt.pecus.mapper;

import org.springframework.stereotype.Component;

import gabrielqt.pecus.dto.request.LotWeighingRequest;
import gabrielqt.pecus.dto.response.LotWeighingResponse;
import gabrielqt.pecus.entity.Lot;
import gabrielqt.pecus.entity.LotWeighing;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LotWeighingMapper {

    public LotWeighing toEntity(LotWeighingRequest request, Lot lot) {
        return LotWeighing.builder()
                .lot(lot)
                .weighingDate(request.weighingDate())
                .sampledAnimalsCount(request.sampledAnimalsCount())
                .totalSampledWeight(request.totalSampledWeight())
                .build();
    }

    public LotWeighingResponse toResponse(LotWeighing lotWeighing) {
        return new LotWeighingResponse(
                lotWeighing.getId(),
                lotWeighing.getLot().getId(),
                lotWeighing.getWeighingDate(),
                lotWeighing.getSampledAnimalsCount(),
                lotWeighing.getAverageSampledWeight()
        );
    }
}
