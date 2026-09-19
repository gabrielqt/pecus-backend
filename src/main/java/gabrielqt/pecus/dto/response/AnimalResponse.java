package gabrielqt.pecus.dto.response;

import java.time.LocalDate;

import gabrielqt.pecus.entity.enums.AnimalCategory;
import gabrielqt.pecus.entity.enums.Sex;
import gabrielqt.pecus.entity.enums.StatusAnimal;

public record AnimalResponse(
    Long id,
    String earTag,
    StatusAnimal status,
    Sex sex,
    Long breedId,
    Long farmId,
    Long lotId,
    LocalDate birthDate,
    AnimalCategory category
) {
}
