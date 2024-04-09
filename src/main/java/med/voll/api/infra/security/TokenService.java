package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-3:00"));
    }
    public String gerarToken(Usuario user){
        try {
            Algorithm algoritmo = Algorithm.HMAC256("123456");
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


}
