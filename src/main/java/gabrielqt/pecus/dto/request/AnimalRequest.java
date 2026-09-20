package gabrielqt.pecus.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import gabrielqt.pecus.entity.Animal;
import gabrielqt.pecus.entity.enums.Sex;
import gabrielqt.pecus.entity.enums.StatusAnimal;

public record AnimalRequest(
    Long id,

    @NotBlank(message = "Número de brinco não informado.")
    String earTag,

    StatusAnimal status,

    @NotNull(message = "Sexo não informado.")
    Sex sex,

    Long breedId,

    @NotNull(message = "Fazenda não informado.")
    Long farmId,

    Long lotId,

    @NotNull(message = "A data de nascimento é obrigatória.")
    LocalDate birthDate,

    AnimalWeighingRequest animalWeighingRequest
) {

    public AnimalRequest { // construtor compacto

        earTag = earTag != null ? earTag.strip() : earTag;
    }
}
