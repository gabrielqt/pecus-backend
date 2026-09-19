package gabrielqt.pecus.endpoint;
import gabrielqt.pecus.dto.request.LoginRequest;
import gabrielqt.pecus.dto.request.RegisterRequest;
import gabrielqt.pecus.dto.response.TokenResponse;
import gabrielqt.pecus.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthEndpoint {
    private final AuthService authService;

    @RequestMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest registerDTO) {

        authService.register(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}