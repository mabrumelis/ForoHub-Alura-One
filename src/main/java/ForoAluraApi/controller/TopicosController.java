package ForoAluraApi.controller;


import ForoAluraApi.domain.cursos.CursoRepository;
import ForoAluraApi.domain.respuestas.*;
import ForoAluraApi.domain.topicos.*;
import ForoAluraApi.domain.usuarios.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;
    private final RespuestaRepository respuestaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public TopicosController(TopicoRepository topicoRepository, CursoRepository cursoRepository,
                            RespuestaRepository respuestaRepository,
                            UsuarioRepository usuarioRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
        this.respuestaRepository = respuestaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<RespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                           UriComponentsBuilder uriComponentsBuilder) {
        var curso = cursoRepository.getReferenceById(datosRegistroTopico.id_curso());
        var autor = usuarioRepository.getReferenceById(datosRegistroTopico.id_autor());
        var topico = topicoRepository.save(new Topico(datosRegistroTopico, curso, autor));
        var datosMostrarUsuario = new MostrarUsuario(autor.getId(), autor.getNombre());
        RespuestaTopico datosRespuestaTopico = new RespuestaTopico(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(), datosMostrarUsuario, curso);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId().toString()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @PostMapping("/{id}/respuestas")
    public ResponseEntity<TopicoResp> registrarRespuesta(@PathVariable Long id,
                                                         @RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta,
                                                         UriComponentsBuilder uriComponentsBuilder) {
        var topico = topicoRepository.getReferenceById(id);
        var autor = usuarioRepository.getReferenceById(datosRegistroRespuesta.id_autor());
        var respuesta = respuestaRepository.save((new Respuesta(datosRegistroRespuesta, topico, autor)));
        var datosMostrarUsuario = new MostrarUsuario(autor.getId(), autor.getNombre());
        var datosMostrarRespuesta = new MostrarRespuesta(respuesta.getId(), respuesta.getMensaje(), respuesta.getFechaCreacion(),
                datosMostrarUsuario);
        TopicoResp datosTopicoConRespuestas = new TopicoResp(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(),
                datosMostrarUsuario, topico.getCurso(), List.of(datosMostrarRespuesta));
        URI url = uriComponentsBuilder.path("/{id}/respuestas/{idResp}").buildAndExpand(topico.getId().toString(),
                respuesta.getId().toString()).toUri();
        return ResponseEntity.created(url).body(datosTopicoConRespuestas);
    }

    @GetMapping
    public ResponseEntity<List<ListadoTopico>> listarTopicos() {
        List<ListadoTopico> topicos = topicoRepository.findAll().stream().map(ListadoTopico::new).toList();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResp> retornarDatosTopico(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        var respuestas = respuestaRepository.findByTopicoId(id);

        List<MostrarRespuesta> datosRespuestas = respuestas.stream()
                .map(respuesta -> new MostrarRespuesta(respuesta.getId(), respuesta.getMensaje(),
                        respuesta.getFechaCreacion(), new MostrarUsuario(respuesta.getAutor().getId(),
                        respuesta.getAutor().getNombre())))
                .collect(Collectors.toList());


        TopicoResp datosTopicoConRespuestas = new TopicoResp(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(),
                new MostrarUsuario(topico.getAutor().getId(), topico.getAutor().getNombre()),
                topico.getCurso(), datosRespuestas);
        return ResponseEntity.ok(datosTopicoConRespuestas);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid ActualizarTopico actualizarTopico) {
        var topico = topicoRepository.getReferenceById(id);
        topico.actualizarTopico(actualizarTopico);
        return ResponseEntity.ok(new RespuestaTopico(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(),
                new MostrarUsuario(topico.getAutor().getId(), topico.getAutor().getNombre()),
                topico.getCurso()));
    }

    @PutMapping("/{id}/respuestas/{idResp}")
    @Transactional
    public ResponseEntity<TopicoRespUdapte> actualizarRespuesta(@PathVariable Long id, @PathVariable Long idResp, @RequestBody
    @Valid ActualizarRespuesta actualizarRespuesta) {
        var topico = topicoRepository.getReferenceById(id);
        var respuesta = respuestaRepository.getReferenceById(idResp);
        respuesta.actualizarRespuesta(actualizarRespuesta);
        TopicoRespUdapte topicoRespUdapte = new TopicoRespUdapte(
                topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(),
                new MostrarUsuario(topico.getAutor().getId(), topico.getAutor().getNombre()), topico.getCurso(),
                new MostrarRespuesta(respuesta.getId(), respuesta.getMensaje(), respuesta.getFechaCreacion(),
                        new MostrarUsuario(topico.getAutor().getId(), topico.getAutor().getNombre())));
        return ResponseEntity.ok(topicoRespUdapte);
    }

    @GetMapping("/{id}/respuestas")
    public ResponseEntity<TopicoResp> returnTopicoResp(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        var respuestas = respuestaRepository.findByTopicoId(id);

        List<MostrarRespuesta> datosRespuestas = respuestas.stream()
                .map(respuesta -> new MostrarRespuesta(respuesta.getId(), respuesta.getMensaje(),
                        respuesta.getFechaCreacion(), new MostrarUsuario(respuesta.getAutor().getId(),
                        respuesta.getAutor().getNombre())))
                .collect(Collectors.toList());
        TopicoResp topicoResp = new TopicoResp(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(),
                new MostrarUsuario(topico.getAutor().getId(), topico.getAutor().getNombre()),
                topico.getCurso(), datosRespuestas);
        return ResponseEntity.ok(topicoResp);
    }

    @GetMapping("/{id}/respuestas/{idResp}")
    public ResponseEntity<TopicoRespuestaSelec> retornarDatosRespuesta(@PathVariable Long id, @PathVariable Long idResp) {
        Topico topico = topicoRepository.getReferenceById(id);
        Respuesta respuesta = respuestaRepository.getReferenceById(idResp);
        var datosMostrarRespuesta = new MostrarRespuesta(respuesta.getId(), respuesta.getMensaje(),
                respuesta.getFechaCreacion(), new MostrarUsuario(respuesta.getAutor().getId(),
                respuesta.getAutor().getNombre()));
        var topicoRespuestaSelec = new TopicoRespuestaSelec(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(),
                new MostrarUsuario(respuesta.getAutor().getId(), respuesta.getAutor().getNombre()),
                topico.getCurso(), List.of(datosMostrarRespuesta));
        return ResponseEntity.ok(topicoRespuestaSelec);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarTopico(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        topicoRepository.delete(topico);
    }

    @DeleteMapping("/{id}/respuestas/{idResp}")
    @Transactional
    public void eliminarRespuestaDeTopico(@PathVariable Long id, @PathVariable Long idResp) {
        var topico = topicoRepository.getReferenceById(id);
        var respuesta = respuestaRepository.getReferenceById(idResp);
        respuestaRepository.delete(respuesta);
    }
}