package med.voll.api.consultas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ConsultasRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime segundoHorario);

    boolean existsByMedicoIdAndDataAndMotivoIsNull(Long idMedico, LocalDateTime data);

    boolean existsByPacienteIdAndDataBetweenAndMotivoIsNull(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime segundoHorario);
}
