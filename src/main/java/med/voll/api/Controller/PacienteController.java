package med.voll.api.Controller;

import jakarta.validation.Valid;
import med.voll.api.pacientes.DadosListagemPaciente;
import med.voll.api.pacientes.DadosPacientes;
import med.voll.api.pacientes.Paciente;
import med.voll.api.pacientes.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	@Autowired
	private PacienteRepository pR;
	
	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosPacientes dados) {
		pR.save(new Paciente(dados));
	}
	
	@GetMapping
	public Page<DadosListagemPaciente> listar(Pageable pagina){
		return pR.findAll(pagina)
				.map(paciente -> new DadosListagemPaciente(paciente));
		
	}
	
	
	
	
}
