package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.consultas.ValidacaoExceptionUsuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorErros {
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity erro404() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity erro400(MethodArgumentNotValidException cause){
		var e = cause.getFieldErrors();//List<FieldError> e = new ArrayList<>();
		
		//return ResponseEntity.badRequest().build(); so vem o HTTP status code 400
		//return ResponseEntity.badRequest().body(e); tá trazendo tudo no JSON
		return ResponseEntity.badRequest().body(e.stream()
				.map(DadosErroDTO::new).toList());
	}
	@ExceptionHandler(ValidacaoExceptionUsuario.class)
	public ResponseEntity erroDeValidacoes(ValidacaoExceptionUsuario cause){
		return ResponseEntity.badRequest().body(cause.getMessage());
	}
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity tratarErro400(HttpMessageNotReadableException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity tratarErroBadCredentials() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity tratarErroAuthentication() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity tratarErroAcessoNegado() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity tratarErro500(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " +ex.getLocalizedMessage());
	}

	private record DadosErroValidacao(String campo, String mensagem) {
		public DadosErroValidacao(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}
	
	private record DadosErroDTO(String field, String message) {
		public DadosErroDTO(FieldError fe) {
			this(fe.getField(), fe.getDefaultMessage());
		}
		
	}

}
