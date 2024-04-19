package med.voll.api.Controller;

import jakarta.validation.Valid;
import med.voll.api.medico.DadosAgendamentoConsulta;
import med.voll.api.medico.DadosDetalhamentoConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("consultas")
public class ConsultaController {


    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(null, null, null, null));
    }

}
