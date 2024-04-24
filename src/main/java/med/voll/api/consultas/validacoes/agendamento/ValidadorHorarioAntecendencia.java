package med.voll.api.consultas.validacoes.agendamento;

import med.voll.api.consultas.DadosAgendamentoConsulta;
import med.voll.api.consultas.ValidacaoExceptionUsuario;

import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ValidadorHorarioAntecendencia implements ValidadorAgendamentoDeConsulta{


    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();
        var dataAtual = LocalDateTime.now();
        var diferenca = Duration.between(dataAtual, dataConsulta).toMinutes();
        if(diferenca < 30)
            throw new ValidacaoExceptionUsuario("""
                    A consulta deve ser agendada com pelo menos 30 minutos de antecedencia
                    """);


    }
}
