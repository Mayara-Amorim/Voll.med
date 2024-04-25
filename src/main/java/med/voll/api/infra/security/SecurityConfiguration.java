package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import jakarta.servlet.Filter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
@Autowired
    private SecurityFilter sF;
//    @Primary
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, Filter securityFilter) throws Exception {
//        return http.csrf(csrf -> csrf.disable())
//                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(req -> {
//                    req.requestMatchers(String.valueOf(HttpMethod.POST), "/login").permitAll();
//                    req.anyRequest().authenticated();
//                })
//                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return
            http.csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(req -> {
//                          req.requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN");
                        req.requestMatchers("/login").permitAll();
                        req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
//                                req.requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN");
                        req.anyRequest().authenticated();
                    })//ele precisa receber um filtro. Qual filtro? O nosso filtro e como segundo parametro o filtro do Spring
                    //E como se dissesse: primeiro o meu filtro e depois o seu
                    .addFilterBefore(sF, UsernamePasswordAuthenticationFilter.class)
                    .build();

}




//    Bean AuthenticationManager: Este bean é criado para fornecer uma instância do AuthenticationManager
//    que pode ser usada para autenticar tokens de autenticação. A configuração específica
//    do AuthenticationManager (como os provedores de autenticação e as políticas de autenticação)
//    é definida na AuthenticationConfiguration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/login"));
//    }

}


