package med.voll.api.medico;

import med.voll.api.consultas.Consulta;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.pacientes.DadosPacientes;
import med.voll.api.pacientes.Paciente;
import net.bytebuddy.implementation.MethodCall;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    public MedicoRepository medicoRepository;
    @Autowired
    private TestEntityManager entityManager;


    @Test
    @DisplayName("Deve devolver null quando não tivermos um medico cadastrado disponivel na data")
    void escolherMedicoAleatorioNaDataCenario1() {
        var proximaSegunda =LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(12, 0);
        var medicoLivre =  medicoRepository.escolherMedicoAleatorioNaData(Especialidade.CARDIOLOGIA, proximaSegunda);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegunda);


      assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deve devolver medico quando ele estivermos disponivel na data")
    void escolherMedicoAleatorioNaDataCenario2() {
        var proximaSegunda =LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(12, 0);
        var medicoLivre =  medicoRepository.escolherMedicoAleatorioNaData(Especialidade.GINECOLOGIA, proximaSegunda);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.GINECOLOGIA);

        assertThat(medicoLivre).isEqualTo(medico);
    }


    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        entityManager.persist(new Consulta(null, medico, paciente, data, null));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        entityManager.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        entityManager.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosPacientes dadosPaciente(String nome, String email, String cpf) {
        return new DadosPacientes(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }



}