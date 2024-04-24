package med.voll.api.consultas.validacoes.cancelamento;

import med.voll.api.consultas.ConsultasRepository;
import med.voll.api.consultas.DadosCancelamentoConsulta;
import med.voll.api.consultas.ValidacaoExceptionUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecendenciaCancelamento implements ValidadorCancelamentoDeConsulta {
    @Autowired
    private ConsultasRepository consultasRepo;
    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var consulta = consultasRepo.getReferenceById(dados.idConsulta());
        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

        if (diferencaEmHoras < 24) {
            throw new ValidacaoExceptionUsuario("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }
    }
}
