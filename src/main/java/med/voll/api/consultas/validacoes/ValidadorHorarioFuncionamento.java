package med.voll.api.consultas.validacoes;

import med.voll.api.consultas.DadosAgendamentoConsulta;
import med.voll.api.consultas.ValidacaoExceptionUsuario;

import java.time.DayOfWeek;
import org.springframework.stereotype.Component;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoDeConsulta{

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesFuncionamentoClinica = dataConsulta.getHour() < 7;
        var depoisEncerramentoClinica = dataConsulta.getHour() > 18;
        if(domingo || antesFuncionamentoClinica || depoisEncerramentoClinica){
            throw new ValidacaoExceptionUsuario("Este horario está fora da agenda comercial");
        }
    }
}
