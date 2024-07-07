package ForoAluraApi.domain.topicos;

import ForoAluraApi.domain.cursos.Curso;
import ForoAluraApi.domain.respuestas.MostrarRespuesta;
import ForoAluraApi.domain.usuarios.MostrarUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record TopicoResp(
        Long id,
        String titulo,
        String mensaje,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime fechaCreacion,
        Status status,
        MostrarUsuario autor,
        Curso curso,
        List<MostrarRespuesta> respuestas
) {
}
