package gabrielqt.pecus.endpoint;

import gabrielqt.pecus.dto.request.LotRequest;
import gabrielqt.pecus.dto.response.LotResponse;
import gabrielqt.pecus.entity.User;
import gabrielqt.pecus.service.LotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lot")
public class LotEndpoint {
    private final LotService lotService;

    @PutMapping
    public ResponseEntity<LotResponse> saveLot(@RequestBody LotRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(lotService.save(request, user));
    }

    @RequestMapping
    public ResponseEntity<Page<LotResponse>> findAllByFarm(Authentication authentication) {}
}
