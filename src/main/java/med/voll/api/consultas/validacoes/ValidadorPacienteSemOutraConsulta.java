package med.voll.api.consultas.validacoes;

import med.voll.api.consultas.DadosAgendamentoConsulta;
import med.voll.api.consultas.ValidacaoExceptionUsuario;
import med.voll.api.pacientes.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsulta implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private PacienteRepository pacienteRepo;

    public void validar(DadosAgendamentoConsulta dados){

        var primeiroHorario = dados.data().withHour(7);
        var segundoHorario = dados.data().withHour(18);
        var pacientePossuioutraConsultaNoDia = pacienteRepo
                .existsByPacientesIdAndDataBetween(dados.idPaciente(), primeiroHorario, segundoHorario);

        if(pacientePossuioutraConsultaNoDia){
            throw new ValidacaoExceptionUsuario("Paciente já possui consulta ativa hoje");
        }

    }


}
