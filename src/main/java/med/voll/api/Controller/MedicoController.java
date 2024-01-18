package med.voll.api.Controller;


import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	@Autowired(required=true)
	private MedicoRepository mr;
	
	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder ucb) {
		var medico = new Medico(dados);
		mr.save(medico);
		var uri = ucb.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
	}
	
	@GetMapping
//	public List<DadosListagemMedico> listar(){
//		return mr.findAll().stream().map(DadosListagemMedico::new).toList();
//		return mr.findAll().stream().map(medico -> new DadosListagemMedico(medico)).toList();
//	}
	public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size= 10, sort = {"nome"}) Pageable paginacao){
	var page = mr.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); 
	return ResponseEntity.ok(page);
	
	}
	@PutMapping
	@Transactional
	public ResponseEntity atualizar (@RequestBody @Valid DadosAtualizarMedico dados) {
		
		//eu criei esse novo DTO pq o DadosAtualizarMedico não traz todas as informações de medico
		//e o atualizar eu quero ver como está meu registro (todas as informaçõs)
		//depois de atualizado
		
		var medico = mr.getReferenceById(dados.id());
		medico.atualizar(dados);
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
		
	}
	
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {
		//mr.deleteById(id);
		var medico = mr.getReferenceById(id);
		medico.excluir();
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity detalhamento(@PathVariable Long id) {
		
		var medico = mr.getReferenceById(id);
		
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
		
	}
	
	
}
