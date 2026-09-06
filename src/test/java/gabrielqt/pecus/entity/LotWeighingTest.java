package gabrielqt.pecus.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;


public class LotWeighingTest {

    @Test
    void deveCalcularMediaCorretamente(){
        LotWeighing lotWeighing = LotWeighing.builder()
                .totalSampledWeight(new BigDecimal("300.00"))
                .sampledAnimalsCount(5)
                .build();

        BigDecimal media = lotWeighing.getAverageSampledWeight();

        assertEquals(new BigDecimal("60.00"), media);
    }

    @Test
    void deveArredondarDuasCasas(){
        LotWeighing lotWeighing = LotWeighing.builder()
                .totalSampledWeight(new BigDecimal("330.00"))
                .sampledAnimalsCount(9)
                .build();
        BigDecimal media = lotWeighing.getAverageSampledWeight(); // 36.6666666667
        assertEquals(new BigDecimal("36.67"), media);
    }
}
