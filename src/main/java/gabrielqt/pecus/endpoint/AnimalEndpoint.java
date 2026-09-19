package gabrielqt.pecus.endpoint;

import gabrielqt.pecus.dto.request.AnimalRequest;
import gabrielqt.pecus.dto.response.AnimalResponse;
import gabrielqt.pecus.entity.User;
import gabrielqt.pecus.service.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animal")
public class AnimalEndpoint {
    private final AnimalService animalService;

    @PutMapping
    @PreAuthorize("@farmSecurity.isOwner(#request.farmId, authentication)")
    public ResponseEntity<AnimalResponse> saveAnimal(@Valid @RequestBody AnimalRequest request, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(animalService.save(request, user));
    }

}
