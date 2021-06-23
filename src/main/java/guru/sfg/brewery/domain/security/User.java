package guru.sfg.brewery.domain.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private  String username;
    private String password;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name= "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name="ROLE_ID", referencedColumnName = "ID")})
    private  Set<Role> roles;


    public Set<Authority> getAuthorities() {
        return this.getRoles()
                .stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    @Transient
    private  Set<Authority> authorities;


    @Builder.Default
    private  boolean accountNonExpired =true;
    @Builder.Default
    private  boolean accountNonLocked=true;
    @Builder.Default
    private  boolean credentialsNonExpired=true;
    @Builder.Default
    private  boolean enabled=true;
}
