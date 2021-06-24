package guru.sfg.brewery.domain.security;

import guru.sfg.brewery.domain.Customer;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails, CredentialsContainer {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private  String username;
    private String password;
    @Builder.Default
    private  boolean accountNonExpired =true;
    @Builder.Default
    private  boolean accountNonLocked=true;
    @Builder.Default
    private  boolean credentialsNonExpired=true;
    @Builder.Default
    private  boolean enabled=true;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name= "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name="ROLE_ID", referencedColumnName = "ID")})
    private  Set<Role> roles;


    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        return this.getRoles()
                .stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .map(authority ->{
                    return new SimpleGrantedAuthority(authority.getPermission());
                })
                .collect(Collectors.toSet());
    }

    @Override
    public void eraseCredentials() {

        this.password=null;
    }
}
