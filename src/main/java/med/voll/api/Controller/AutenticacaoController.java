package med.voll.api.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import med.voll.api.usuario.*;


@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager am;

    @PostMapping
    public  ResponseEntity efetuarLogin(RequestBody @Valid DadosAutenticacao data){
        var token = new UsernamePasswordAuthenticationToken(data.login, data.senha);
       var authentication = am.authenticate(token);
       return null;
    }



}
