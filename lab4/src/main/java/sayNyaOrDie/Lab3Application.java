package sayNyaOrDie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sayNyaOrDie.entities.Employee;
import sayNyaOrDie.repositories.EmployeeRepository;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity
public class Lab3Application {
    public static void main(String[] args) {
        SpringApplication.run(Lab3Application.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(EmployeeRepository repository, PasswordEncoder passwordEncoder) {
        // добавляем тестовых юзеров
        if (repository.findByName("admin") == null) {
            repository.save(new Employee(0, "admin", new Date(LocalDate.of(2002, 9, 19).toEpochDay()), passwordEncoder.encode("admin"), true));
        }
        if (repository.findByName("user") == null) {
            repository.save(new Employee(0, "user", new Date(LocalDate.of(1993,11,15).toEpochDay()), passwordEncoder.encode("1111"), false));
        }
        return new EmployeeUserDetailsService(repository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        var http = httpSecurity.csrf(csrf -> csrf.disable()) // эту штуку нужно вырубить иначе в сваггере не будет работать (403 код всегда будет вылетать)
            .formLogin(form -> form.defaultSuccessUrl("/swagger-ui/index.html").permitAll()) // переправить при успешном логине юзера в сваггер
            .authorizeHttpRequests(authorize -> authorize
                    .anyRequest().authenticated()); // любой реквест должен быть аутентифицирован (нужен для того чтобы указать permitAll для логин формы)
        return http.build();
    }
}
