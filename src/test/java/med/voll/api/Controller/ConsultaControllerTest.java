package med.voll.api.Controller;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.consultas.AgendaDeConsultas;
import med.voll.api.consultas.DadosAgendamentoConsulta;
import med.voll.api.consultas.DadosDetalhamentoConsulta;
import med.voll.api.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    // nos genercs devo colocar o mesmo tipo que recebo ou devolve no controller
    private JacksonTester<DadosAgendamentoConsulta> jsonTesterReq;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> jsonTesterResp;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Dar erro 400 quando dados invalidos")
    @WithMockUser
    void agendarCenario1() throws Exception {
        var response =mvc.perform(post("/consultas")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Dar http status 200 e devolver o detalhamento quando info validas")
    @WithMockUser
    void agendarCenario2() throws Exception {

        LocalDateTime data = LocalDateTime.now().plusHours(1);
        Especialidade especialidade = Especialidade.GINECOLOGIA;
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 2l, data);
                when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);
        var response = mvc.perform(
                post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTesterReq.write(new DadosAgendamentoConsulta(1l, 2l, data, especialidade)).getJson())
                )
                        .andReturn()
                        .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = jsonTesterResp.write(dadosDetalhamento).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}