package med.voll.api.consultas.validacoes.agendamento;

import med.voll.api.consultas.DadosAgendamentoConsulta;
import med.voll.api.consultas.ValidacaoExceptionUsuario;
import med.voll.api.pacientes.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta{
    @Autowired
    private PacienteRepository pacienteRepo;

    public void validar(DadosAgendamentoConsulta dados){
        var pacienteAtivo = pacienteRepo.findAtivoById(dados.idPaciente());
        if (!pacienteAtivo){
            throw new ValidacaoExceptionUsuario("Paciente não está ativo ou não existe");
        }
    }
}
