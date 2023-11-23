package med.voll.api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.pacientes.DadosListagemPaciente;
import med.voll.api.pacientes.DadosPacientes;
import med.voll.api.pacientes.Paciente;
import med.voll.api.pacientes.PacienteRepository;
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
