package sayNyaOrDie.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Entity
@Table(name = "employee") // имплементим этот интерфейс для того чтобы юзать класс Employee как юзера в Spring Security
public class Employee implements UserDetails {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private long id;
    @Setter
    @Getter
    @Column(name = "employee_name")
    private String name;
    @Setter
    @Getter
    @Column(name = "employee_birthdate")
    private Date dateOfBirth;
    @Setter
    @Getter
    @Column(name = "employee_password")
    private String hashedPassword;
    @Setter
    @Getter
    @Column(name = "employee_admin")
    private boolean admin;

    public Employee(long id, String name, Date dateOfBirth, String hashedPassword, boolean admin) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.hashedPassword = hashedPassword;
        this.admin = admin;
    }

    public Employee() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Определяем роли у юзеров
        var authorities = new ArrayList<String>();
        authorities.add("ROLE_USER");
        if (admin) {
            authorities.add("ROLE_ADMIN");
        }
        return AuthorityUtils.createAuthorityList(authorities);
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
