package med.voll.api.consultas;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record DadosCancelamentoConsulta(
        @NotNull
        Long idConsulta,

        @NotNull
        Motivo motivo
) {

}
