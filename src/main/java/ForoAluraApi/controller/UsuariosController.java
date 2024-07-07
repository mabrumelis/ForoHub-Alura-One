package ForoAluraApi.controller;

import ForoAluraApi.domain.usuarios.DatosRegistroUsuario;
import ForoAluraApi.domain.usuarios.Usuario;
import ForoAluraApi.domain.usuarios.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newusuario")

public class UsuariosController {
    private final UsuarioRepository usuariosRepository;

    @Autowired
    public UsuariosController(UsuarioRepository usuarioRepository){
        this.usuariosRepository = usuarioRepository;
    }


    @PostMapping
    public void registrarUsurio(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(datosRegistroUsuario.contrasena());
        var usuario = usuariosRepository.save(new Usuario(datosRegistroUsuario.nombre(),
                datosRegistroUsuario.email(),passwordEncriptada));
        System.out.println(usuario);
    }
}
