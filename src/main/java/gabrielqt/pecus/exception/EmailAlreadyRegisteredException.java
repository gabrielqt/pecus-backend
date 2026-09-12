package gabrielqt.pecus.exception;

public class EmailAlreadyRegisteredException extends BusinessException {
  public EmailAlreadyRegisteredException(String email) {
    super(
            "Email already registered for user " + email
    );
  }
}
