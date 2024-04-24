package med.voll.api.Controller;

import jakarta.validation.Valid;
import med.voll.api.consultas.AgendaDeConsultas;
import med.voll.api.consultas.DadosAgendamentoConsulta;
import med.voll.api.consultas.DadosCancelamentoConsulta;
import med.voll.api.consultas.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("consultas")
public class ConsultaController {
    @Autowired
    private AgendaDeConsultas agConsulta;


    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
       var dto = agConsulta.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agConsulta.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

}
