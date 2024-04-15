package med.voll.api.Controller;

import jakarta.validation.Valid;
import med.voll.api.infra.security.DadosTokenDTO;
import med.voll.api.infra.security.TokenService;
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
    @Autowired
    private TokenService tS;
    //Método efetuarLogin: Este método é responsável por receber as credenciais do usuário, criar
//um token de autenticação com essas credenciais, autenticar o token usando o AuthenticationManager,
// e retornar um token de acesso ao usuário.
    @PostMapping
    public  ResponseEntity efetuarLogin( @RequestBody @Valid DadosAutenticacao data){
        var authToken = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
       var authentication = am.authenticate(authToken);
       var tokenJWT = tS.gerarToken((Usuario) authentication.getPrincipal());

       return ResponseEntity.ok(new DadosTokenDTO(tokenJWT));
    }



}
