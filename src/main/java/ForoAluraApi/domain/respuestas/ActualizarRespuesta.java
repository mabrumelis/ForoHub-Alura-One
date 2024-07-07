package ForoAluraApi.domain.respuestas;

import ForoAluraApi.domain.topicos.Status;
import jakarta.validation.constraints.NotNull;

public record ActualizarRespuesta(@NotNull String mensaje, @NotNull Status status) {
}
