package gabrielqt.pecus.endpoint;

import gabrielqt.pecus.dto.request.FarmRequest;
import gabrielqt.pecus.dto.response.FarmResponse;
import gabrielqt.pecus.entity.Farm;
import gabrielqt.pecus.entity.User;
import gabrielqt.pecus.entity.enums.Role;
import gabrielqt.pecus.service.FarmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/farm")
public class FarmEndpoint {
    private final FarmService farmService;

    @PutMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<FarmResponse> saveFarm(@Valid @RequestBody FarmRequest farmRequest,
                                                 Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(farmService.save(farmRequest, user));
    }

    @RequestMapping
    public ResponseEntity<Page<FarmResponse>> findAllByUser(Pageable pageable, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(farmService.findByUser(pageable, user));
    }


    @RequestMapping("/{id}")
    public ResponseEntity<FarmResponse> findById(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(farmService.findById(id, user));
    }

}
