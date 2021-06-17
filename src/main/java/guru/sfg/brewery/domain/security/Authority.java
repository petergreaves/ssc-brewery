package guru.sfg.brewery.domain.security;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Authority {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;

    private String role;

}
