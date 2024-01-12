package med.voll.api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

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
	
	private record DadosErroDTO(String field, String message) {
		public DadosErroDTO(FieldError fe) {
			this(fe.getField(), fe.getDefaultMessage());
		}
		
	}

}
