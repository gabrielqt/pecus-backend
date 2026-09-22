package gabrielqt.pecus.endpoint;

import gabrielqt.pecus.dto.request.AnimalWeighingRequest;
import gabrielqt.pecus.dto.response.AnimalWeighingResponse;
import gabrielqt.pecus.service.AnimalWeighingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animal-weighing")
public class AnimalWeighingEndpoint {
    private final AnimalWeighingService animalWeighingService;

    @PostMapping
    @PreAuthorize("@animalSecurity.canAccess(#request.animalId, authentication)")
    public ResponseEntity<AnimalWeighingResponse> saveAnimalWeighing(@Valid @RequestBody AnimalWeighingRequest request) {

        return ResponseEntity.ok(animalWeighingService.save(request));
    }

    @GetMapping("/animal/{animalId}")
    @PreAuthorize("@animalSecurity.canAccess(#animalId, authentication)")
    public ResponseEntity<Page<AnimalWeighingResponse>> findAllByAnimalId(
            @PathVariable Long animalId,
            @PageableDefault(sort = "weighingDate", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(animalWeighingService.findAllByAnimalId(animalId, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@animalSecurity.canAccessWeighing(#id, authentication)")
    public ResponseEntity<AnimalWeighingResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok(animalWeighingService.findResponseById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@animalSecurity.canAccess(#animalId, authentication)")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        animalWeighingService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
