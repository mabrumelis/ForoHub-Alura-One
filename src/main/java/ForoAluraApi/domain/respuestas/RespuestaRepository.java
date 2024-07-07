package ForoAluraApi.domain.respuestas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


import java.util.List;
@Component
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    List<Respuesta> findByTopicoId(Long topicoId);
}