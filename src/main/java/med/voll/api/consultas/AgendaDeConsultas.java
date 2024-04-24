package med.voll.api.consultas;

import med.voll.api.consultas.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.consultas.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.pacientes.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultasRepository consultaRepo;
    @Autowired
    private PacienteRepository pacienteRepo;
    @Autowired
    private MedicoRepository medicoRepo;
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;
    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){
        //validacoes de integridade
        if(!pacienteRepo.existsById(dados.idPaciente())){
            throw new ValidacaoExceptionUsuario("Id do paciente não existe!");

        }if (dados.idMedico()!= null && !medicoRepo.existsById(dados.idMedico())){
            throw new ValidacaoExceptionUsuario("Id do medico não existe!");
        }


        validadores.forEach(v-> v.validar(dados));

        var paciente = pacienteRepo.findById(dados.idPaciente()).get();
        //var medico = medicoRepo.findById(dados.idMedico());

        var medico = escolherMedico(dados);
        if(medico == null){
            throw new ValidacaoExceptionUsuario("Não temos um medico nessa data");
        }

        //no final de tudoo que eu preciso eh salvar no banco um objeto do tipo Consulta();
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepo.save(consulta);
        //o medico e o paciente não estão vindo na requisição apenas o id, então
        //vamos pecisar carregar essa entidades para criar o objeto.
        //colocamos um get() pq finfById devolve um Optional<T>

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null){
          return  medicoRepo.getReferenceById(dados.idMedico());
        }
        if(dados.especialidade() == null){
          throw new ValidacaoExceptionUsuario("Quando não existe um medico escolhido," +
                  "a especialidade é obrigatotia");
        }
       return medicoRepo.escolherMedicoAleatorioNaData(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepo.existsById(dados.idConsulta())) {
            throw new ValidacaoExceptionUsuario("Id da consulta informado não existe!");
        }
        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepo.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

}
