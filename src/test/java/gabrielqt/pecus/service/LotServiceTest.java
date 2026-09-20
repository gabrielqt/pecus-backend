package gabrielqt.pecus.service;

import gabrielqt.pecus.dto.request.LotRequest;
import gabrielqt.pecus.dto.response.LotResponse;
import gabrielqt.pecus.entity.Farm;
import gabrielqt.pecus.entity.Lot;
import gabrielqt.pecus.exception.ObjectNotFoundException;
import gabrielqt.pecus.mapper.LotMapper;
import gabrielqt.pecus.repository.LotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LotServiceTest {

    @Mock
    private LotRepository lotRepository;

    @Mock
    private FarmService farmService;

    @Mock
    private LotMapper lotMapper;

    @InjectMocks
    private LotService lotService;


    @Test
    void deveSalvarLoteQuandoDadosValidos() {

        LotRequest request = new LotRequest(null, "Piquete 3", "Pasto A", 1L);
        Farm farm = new Farm();
        Lot lot = Lot.builder().id(1L).name("Piquete 3").paddock("Pasto A").farm(farm).build();
        LotResponse expectedResponse = new LotResponse(lot.getId(), lot.getName(), lot.getPaddock(), lot.getFarm().getId());

        when(farmService.findById(1L)).thenReturn(farm);
        when(lotMapper.toEntity(request, farm)).thenReturn(lot);
        when(lotRepository.save(lot)).thenReturn(lot);
        when(lotMapper.toResponse(lot)).thenReturn(expectedResponse);

        LotResponse result = lotService.save(request, null);

        assertEquals(expectedResponse, result);
        verify(lotRepository).save(lot);
    }

    @Test
    void naoDeveSalvarLoteQuandoDadosInvalidos() {

        LotRequest request = new LotRequest(3L, "abc", "def", 5L);

        when(farmService.findById(5L)).thenReturn(new Farm());
        when(lotRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> lotService.save(request, null));
    }

}