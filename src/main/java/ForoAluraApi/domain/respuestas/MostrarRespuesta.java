package ForoAluraApi.domain.respuestas;

import ForoAluraApi.domain.usuarios.MostrarUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record MostrarRespuesta(
        Long id,
        String mensaje,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime fechaCreacion,
        MostrarUsuario autor) {
}
