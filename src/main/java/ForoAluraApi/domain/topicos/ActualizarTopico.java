package ForoAluraApi.domain.topicos;

import jakarta.validation.constraints.NotNull;

public record ActualizarTopico(
        @NotNull
        String titulo,
        @NotNull
        String mensaje,
        @NotNull
        Status status) {
}

