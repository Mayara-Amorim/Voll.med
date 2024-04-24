package med.voll.api.consultas.validacoes;

import med.voll.api.consultas.DadosAgendamentoConsulta;
import med.voll.api.consultas.ValidacaoExceptionUsuario;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta{
    @Autowired
    private MedicoRepository medicoRepo;
    public void validar(DadosAgendamentoConsulta dados){
        if(dados.idMedico() == null){
            return;
        }

        var medicoAtivo = medicoRepo.findAtivoById(dados.idMedico());
        if (!medicoAtivo){
            throw new ValidacaoExceptionUsuario("Medico não está ativo ou não existe");
        }
    }
}
