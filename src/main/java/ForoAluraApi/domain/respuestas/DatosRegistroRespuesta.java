package ForoAluraApi.domain.respuestas;

import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuesta(
        @NotNull
        String mensaje,
        @NotNull
        Long id_autor
) {
}