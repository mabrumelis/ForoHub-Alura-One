package ForoAluraApi.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosRegistroUsuario (
    @NotBlank
    String nombre,
    @NotBlank
    @Email
    String email,
    @NotBlank
    @Size(min = 4, max = 20)
    String contrasena) {
}
