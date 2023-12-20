package med.voll.api.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import med.voll.api.medico.DadosAtualizarMedico;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosDetalhamentoMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	@Autowired
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
