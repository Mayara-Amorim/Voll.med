package med.voll.api.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import jakarta.validation.Valid;
import med.voll.api.medico.DadosAtualizarMedico;
import med.voll.api.medico.DadosCadastroMedico;
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
	public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
		mr.save(new Medico(dados));
	}
	
	@GetMapping
//	public List<DadosListagemMedico> listar(){
//		return mr.findAll().stream().map(DadosListagemMedico::new).toList();
//		return mr.findAll().stream().map(medico -> new DadosListagemMedico(medico)).toList();
//	}
	public Page<DadosListagemMedico> listar(@PageableDefault(size= 10, sort = {"nome"}) Pageable paginacao){
	return mr.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); 
	
	}
	@PutMapping
	@Transactional
	public void atualizar (@RequestBody @Valid DadosAtualizarMedico dados) {
		
		var medico = mr.getReferenceById(dados.id());
		medico.atualizar(dados);
		
	}
	
	
	@DeleteMapping("/{id}")
	@Transactional
	public void excluir(@PathVariable Long id) {
		//mr.deleteById(id);
		var medico = mr.getReferenceById(id);
		medico.excluir();
	}
	
	
	
	
}
