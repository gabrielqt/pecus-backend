package gabrielqt.pecus.service;

import gabrielqt.pecus.dto.request.AnimalWeighingRequest;
import gabrielqt.pecus.dto.response.AnimalWeighingResponse;
import gabrielqt.pecus.entity.Animal;
import gabrielqt.pecus.entity.AnimalWeighing;
import gabrielqt.pecus.exception.BusinessException;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.mapper.AnimalWeighingMapper;
import gabrielqt.pecus.repository.AnimalRepository;
import gabrielqt.pecus.repository.AnimalWeighingRepository;
import gabrielqt.pecus.validator.AnimalWeighingValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AnimalWeighingServiceTest {

    private static final LocalDate DATA = LocalDate.of(2026, 1, 10);

    @Mock private AnimalWeighingRepository animalWeighingRepository;
    @Mock private AnimalWeighingMapper animalWeighingMapper;
    @Mock private AnimalRepository animalRepository;

    private AnimalWeighingService animalWeighingService;

    // validator real com o repository mockado, pra continuar testando a regra de data duplicada
    @BeforeEach
    void setUp() {

        animalWeighingService = new AnimalWeighingService(animalWeighingRepository, animalWeighingMapper,
                new AnimalWeighingValidator(animalWeighingRepository), animalRepository);
    }

    @Test
    void deveSalvarPesagemQuandoDadosValidos() {

        AnimalWeighingRequest request = new AnimalWeighingRequest(1L, BigDecimal.valueOf(300), DATA);
        Animal animal = Animal.builder().id(1L).build();
        AnimalWeighing weighing = AnimalWeighing.builder().animal(animal).weight(BigDecimal.valueOf(300)).weighingDate(DATA).build();
        AnimalWeighingResponse expectedResponse = new AnimalWeighingResponse(10L, 1L, BigDecimal.valueOf(300), DATA);

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(animalWeighingRepository.existsByAnimalIdAndWeighingDate(1L, DATA)).thenReturn(false);
        when(animalWeighingMapper.toEntity(request, animal)).thenReturn(weighing);
        when(animalWeighingRepository.save(weighing)).thenReturn(weighing);
        when(animalWeighingMapper.toResponse(weighing)).thenReturn(expectedResponse);

        AnimalWeighingResponse result = animalWeighingService.save(request);

        assertEquals(expectedResponse, result);
        verify(animalWeighingRepository).save(weighing);
    }

    @Test
    void deveValidarDataPeloIdDoAnimalSalvoNoPesoInicial() {

        // no peso inicial o request vem sem animalId, quem sabe o id é o animal já salvo
        AnimalWeighingRequest request = new AnimalWeighingRequest(null, BigDecimal.valueOf(50), DATA);
        Animal animal = Animal.builder().id(7L).build();
        AnimalWeighing weighing = new AnimalWeighing();

        when(animalWeighingRepository.existsByAnimalIdAndWeighingDate(7L, DATA)).thenReturn(false);
        when(animalWeighingMapper.toEntity(request, animal)).thenReturn(weighing);
        when(animalWeighingRepository.save(weighing)).thenReturn(weighing);

        animalWeighingService.save(request, animal);

        verify(animalWeighingRepository).existsByAnimalIdAndWeighingDate(7L, DATA);
        verify(animalWeighingRepository).save(weighing);
    }

    @Test
    void naoDeveSalvarPesagemQuandoAnimalNaoExiste() {

        AnimalWeighingRequest request = new AnimalWeighingRequest(99L, BigDecimal.valueOf(300), DATA);

        when(animalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> animalWeighingService.save(request));

        verify(animalWeighingRepository, never()).save(any());
    }

    @Test
    void naoDeveSalvarPesagemQuandoJaExisteNaMesmaData() {

        AnimalWeighingRequest request = new AnimalWeighingRequest(1L, BigDecimal.valueOf(300), DATA);
        Animal animal = Animal.builder().id(1L).build();

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(animalWeighingRepository.existsByAnimalIdAndWeighingDate(1L, DATA)).thenReturn(true);

        assertThrows(BusinessException.class, () -> animalWeighingService.save(request));

        verify(animalWeighingRepository, never()).save(any());
    }

    @Test
    void deveBuscarPesagemPorId() {

        AnimalWeighing weighing = AnimalWeighing.builder().id(10L).build();
        AnimalWeighingResponse expectedResponse = new AnimalWeighingResponse(10L, 1L, BigDecimal.valueOf(300), DATA);

        when(animalWeighingRepository.findById(10L)).thenReturn(Optional.of(weighing));
        when(animalWeighingMapper.toResponse(weighing)).thenReturn(expectedResponse);

        assertEquals(expectedResponse, animalWeighingService.findResponseById(10L));
    }

    @Test
    void naoDeveBuscarPesagemInexistente() {

        when(animalWeighingRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> animalWeighingService.findById(10L));
    }
}
