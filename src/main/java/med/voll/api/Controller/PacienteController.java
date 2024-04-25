package med.voll.api.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosAtualizarMedico;
import med.voll.api.medico.DadosDetalhamentoMedico;
import med.voll.api.pacientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
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
	@PutMapping
	@Transactional
	public ResponseEntity atualizar (@RequestBody @Valid DadosAtualizarPaciente dados) {

		//eu criei esse novo DTO pq o DadosAtualizarpaciente não traz todas as informações de paciente
		//e o atualizar eu quero ver como está meu registro (todas as informaçõs)
		//depois de atualizado

		var paciente = pR.getReferenceById(dados.id());
		paciente.atualizar(dados);
		return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));

	}

	//@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {
		//mr.deleteById(id);
		var paciente = pR.getReferenceById(id);
		paciente.excluir();
		return ResponseEntity.noContent().build();

	}

	@GetMapping("/{id}")
	public ResponseEntity detalhamento(@PathVariable Long id) {

		var paciente = pR.getReferenceById(id);

		return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));

	}






}
