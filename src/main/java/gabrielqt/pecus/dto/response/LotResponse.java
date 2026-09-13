package gabrielqt.pecus.dto.response;

public record LotResponse(
        Long id,
        String name,
        String paddock,
        Long farmId
) {
}
