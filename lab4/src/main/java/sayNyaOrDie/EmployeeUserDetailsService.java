package sayNyaOrDie;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sayNyaOrDie.repositories.EmployeeRepository;

// имплементим UserDetailsService как обёртку над EmployeeRepository
// для того чтобы юзать класс Employee как юзера в Spring Security
public class EmployeeUserDetailsService implements UserDetailsService {
    private EmployeeRepository repository;

    public EmployeeUserDetailsService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByName(username);
    }
}
