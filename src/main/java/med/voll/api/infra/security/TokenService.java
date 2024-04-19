package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
@Value("${api.security.secret}")
    private String secret;
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
    public String gerarToken(Usuario user){
        System.out.println(secret);
        try {

            Algorithm algoritmo = Algorithm.HMAC256(secret);
           return JWT.create()
                    .withIssuer("API Voll.med")
                   .withSubject(user.getLogin())
                   //.withClaim("id", user.getId())
                   .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException e){
           throw new RuntimeException("Houve um erro ao gerar token", e);
        }

    }

    public String getSubject(String tokenTWT){

        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
           return JWT.require(algoritmo)
                    // specify any specific claim validations
                    .withIssuer("API Voll.med")
                   .build()
                   .verify(tokenTWT)
                   .getSubject();


        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido");
        }
        
    }

}
