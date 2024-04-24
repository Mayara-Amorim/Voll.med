package med.voll.api.consultas;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long idMedico, Long idPacente, LocalDateTime data) {
}
