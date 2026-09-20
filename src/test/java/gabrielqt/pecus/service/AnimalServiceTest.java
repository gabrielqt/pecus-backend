package gabrielqt.pecus.service;

import gabrielqt.pecus.dto.request.AnimalRequest;
import gabrielqt.pecus.dto.request.AnimalWeighingRequest;
import gabrielqt.pecus.dto.response.AnimalResponse;
import gabrielqt.pecus.entity.Animal;
import gabrielqt.pecus.entity.Farm;
import gabrielqt.pecus.entity.enums.Sex;
import gabrielqt.pecus.entity.enums.StatusAnimal;
import gabrielqt.pecus.mapper.AnimalMapper;
import gabrielqt.pecus.repository.AnimalRepository;
import gabrielqt.pecus.repository.BreedRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock private AnimalRepository animalRepository;
    @Mock private AnimalMapper animalMapper;
    @Mock private FarmService farmService;
    @Mock private LotService lotService;
    @Mock private BreedRepository breedRepository;
    @Mock private AnimalWeighingService animalWeighingService;

    @InjectMocks private AnimalService animalService;

    @Test
    void deveSalvarAnimalComPesoInicial() {

        AnimalWeighingRequest pesoRequest = new AnimalWeighingRequest(
                null, BigDecimal.valueOf(50), LocalDate.of(2026, 1, 10)
        );
        AnimalRequest request = new AnimalRequest(
                null,
                "BOI-001",
                null,
                gabrielqt.pecus.entity.enums.Sex.MALE,
                null,
                1L,
                null,
                java.time.LocalDate.of(2023, 1, 1),
                pesoRequest
        );

        Farm farm = new Farm();
        Animal animalSalvo = new Animal();
        AnimalResponse expectedResponse = new AnimalResponse(
                null,"BOI-001", StatusAnimal.ACTIVE, Sex.MALE, null, null, null, null, null
        );

        when(animalRepository.existsByFarmIdAndEartag(1L, "BOI-001")).thenReturn(false);
        when(farmService.findById(1L)).thenReturn(farm);
        when(animalMapper.toEntity(request, farm, null, null)).thenReturn(new Animal());
        when(animalRepository.save(any(Animal.class))).thenReturn(animalSalvo);
        when(animalMapper.toResponse(animalSalvo)).thenReturn(expectedResponse);

        AnimalResponse result = animalService.save(request, null);

        assertEquals(expectedResponse, result);
        verify(animalRepository).save(any(Animal.class));               // salvou o animal
        verify(animalWeighingService).save(pesoRequest, animalSalvo);   // salvou o peso, com o animal salvo
    }

    @Test
    void deveSalvarAnimalSemPesoQuandoNaoInformado() {
        AnimalRequest request = new AnimalRequest(
                null, "BOI-002", null, Sex.MALE, null, 1L, null,
                LocalDate.of(2023, 1, 1),
                null    // ← SEM peso
        );

        when(animalRepository.existsByFarmIdAndEartag(1L, "BOI-002")).thenReturn(false);
        when(farmService.findById(1L)).thenReturn(new Farm());
        when(animalRepository.save(any())).thenReturn(new Animal());
        when(animalMapper.toResponse(any())).thenReturn(new AnimalResponse(
                null,"BOI-001", StatusAnimal.ACTIVE, Sex.MALE, null, null, null, null, null
        ));

        animalService.save(request, null);

        verify(animalWeighingService, never()).save(any(), any());
    }
}
