package gabrielqt.pecus.exception;

public class ObjectNotFoundException extends BusinessException {
  public ObjectNotFoundException(Class<?> clazz, Object id) {
    super("%s not found with id %s".formatted(clazz.getSimpleName(), id));
  }
  public ObjectNotFoundException(String message) {
    super(message);
  }
}
