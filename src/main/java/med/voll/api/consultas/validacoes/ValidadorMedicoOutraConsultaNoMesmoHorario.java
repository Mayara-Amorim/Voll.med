package med.voll.api.consultas.validacoes;

import med.voll.api.consultas.ConsultasRepository;
import med.voll.api.consultas.DadosAgendamentoConsulta;
import med.voll.api.consultas.ValidacaoExceptionUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta{
    @Autowired
    private ConsultasRepository consultaRepo;


    public void validar(DadosAgendamentoConsulta dados){
       var existeConsultaMedicoMesmoHorario = consultaRepo.existsByMedicoIdAndData(dados.idMedico(), dados.data());

       if(existeConsultaMedicoMesmoHorario){
           throw new ValidacaoExceptionUsuario("Medico com consulta no mesmo horario");
       }
    }
}
