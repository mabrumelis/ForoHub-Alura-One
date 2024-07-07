package ForoAluraApi.domain.topicos;

import ForoAluraApi.domain.cursos.Curso;
import ForoAluraApi.domain.usuarios.MostrarUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime fechaCreacion,
        String status,
        MostrarUsuario autor,
        Curso curso) {

    public ListadoTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus().toString(),
                new MostrarUsuario(topico.getAutor().getId(),topico.getAutor().getNombre()), topico.getCurso());
    }
}