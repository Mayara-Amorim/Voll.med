package med.voll.api.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorErros {
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity erro404() {
		return ResponseEntity.notFound().build();
	}

}
