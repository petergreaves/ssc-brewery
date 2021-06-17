package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;// = new BCryptPasswordEncoder(15);

    @Override
    public void run(String... args) throws Exception {
        loadAuthoritiesAndUsers();

    }

    private void loadAuthoritiesAndUsers(){
        if (authorityRepository.count() == 0){

            Authority admin=authorityRepository.save(Authority.builder().role("ADMIN").build());
            Authority user=authorityRepository.save(Authority.builder().role("USER").build());
            Authority customer=authorityRepository.save(Authority.builder().role("CUSTOMER").build());


            userRepository.save(User
                    .builder()
                    .username("scott")
                    .password(passwordEncoder.encode("tiger"))
                    .authority(customer)
                    .build());
            userRepository.save(User
                    .builder()
                    .username("user")
                    .password(passwordEncoder.encode("password"))
                    .authority(user)
                    .build());
            userRepository.save(User
                    .builder()
                    .username("admin")
                    .password(passwordEncoder.encode("bar"))
                    .authority(admin)
                    .build());

            userRepository.save(User
                    .builder()
                    .username("spring")
                    .password(passwordEncoder.encode("guru"))
                    .authority(admin)
                    .build());

            log.info("Loaded authorities and users.");
        }

    }

}
