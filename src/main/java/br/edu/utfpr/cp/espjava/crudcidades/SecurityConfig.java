package br.edu.utfpr.cp.espjava.crudcidades;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriterFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/h2-console/**").permitAll();
                    auth.requestMatchers("/").hasAnyRole("listar", "admin");
                    auth.requestMatchers("/criar", "/excluir", "/prepararAlterar", "/alterar").hasRole("admin");
                    auth.anyRequest().denyAll();
                })
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                //.formLogin(form -> form.loginPage("/login.html").permitAll())
                .formLogin(FormLoginConfigurer::permitAll)
                .logout(LogoutConfigurer::permitAll)
                //.addFilterBefore(new CustomSecurityHeadersFilter(), HeaderWriterFilter.class)
                .headers(headers -> headers
                        .frameOptions().sameOrigin()  // Permite iframes do mesmo domínio
                )
                .build();
    }


//    @EventListener(ApplicationReadyEvent.class)
//    public void printSenhas(){
//        System.out.printf(this.cifrador().encode("teste123"));
//    }

    @Bean
    public PasswordEncoder cifrador() {
        return new BCryptPasswordEncoder();
    }
}