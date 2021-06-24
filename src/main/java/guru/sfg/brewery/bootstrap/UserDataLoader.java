package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.RoleRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        loadAuthoritiesAndUsers();

    }

    private void loadAuthoritiesAndUsers(){
        if (authorityRepository.count() == 0){

            //beer authorities

            Authority createBeer=authorityRepository.save(Authority.builder().permission("beer.create").build());
            Authority readBeer=authorityRepository.save(Authority.builder().permission("beer.read").build());
            Authority updateBeer=authorityRepository.save(Authority.builder().permission("beer.update").build());
            Authority deleteBeer=authorityRepository.save(Authority.builder().permission("beer.delete").build());

            //brewery auth
            Authority createBrewery=authorityRepository.save(Authority.builder().permission("brewery.create").build());
            Authority readBrewery=authorityRepository.save(Authority.builder().permission("brewery.read").build());
            Authority updateBrewery=authorityRepository.save(Authority.builder().permission("brewery.update").build());
            Authority deleteBrewery=authorityRepository.save(Authority.builder().permission("brewery.delete").build());

            //customer auths

            Authority createCustomer=authorityRepository.save(Authority.builder().permission("customer.create").build());
            Authority readCustomer=authorityRepository.save(Authority.builder().permission("customer.read").build());
            Authority updateCustomer=authorityRepository.save(Authority.builder().permission("customer.update").build());
            Authority deleteCustomer=authorityRepository.save(Authority.builder().permission("customer.delete").build());


            //order auths for admin

            Authority createOrder=authorityRepository.save(Authority.builder().permission("order.create").build());
            Authority readOrder=authorityRepository.save(Authority.builder().permission("order.read").build());
            Authority updateOrder=authorityRepository.save(Authority.builder().permission("order.update").build());
            Authority deleteOrder=authorityRepository.save(Authority.builder().permission("order.delete").build());

            //order auths for customer

            Authority createOrderCustomer=authorityRepository.save(Authority.builder().permission("customer.order.create").build());
            Authority readOrderCustomer=authorityRepository.save(Authority.builder().permission("customer.order.read").build());
            Authority updateOrderCustomer=authorityRepository.save(Authority.builder().permission("customer.order.update").build());
            Authority deleteOrderCustomer=authorityRepository.save(Authority.builder().permission("customer.order.delete").build());

            Role adminRole=Role.builder()
                    .authorities(Set.of(createBeer,readBeer,updateBeer,deleteBeer,
                            createBrewery, updateBrewery,readBrewery,deleteBrewery,
                            createCustomer,readCustomer,updateCustomer,deleteCustomer,
                            createOrder, deleteOrder, updateOrder, readOrder)).build();

            Role customerRole=Role.builder().authorities(Set.of(readBeer, readCustomer, readBrewery,
                    createOrderCustomer, deleteOrderCustomer, updateOrderCustomer, readOrderCustomer)).build();

            Role userRole=Role.builder().authorities(Set.of(readBeer)).build();

            roleRepository.saveAll(Arrays.asList(adminRole, customerRole,userRole));


            userRepository.save(User
                    .builder()
                    .username("scott")
                    .password(passwordEncoder.encode("tiger"))
                    .role(customerRole)
                    .build());
            userRepository.save(User
                    .builder()
                    .username("user")
                    .password(passwordEncoder.encode("password"))
                    .role(userRole)
                    .build());
            userRepository.save(User
                    .builder()
                    .username("admin")
                    .password(passwordEncoder.encode("bar"))
                    .role(adminRole)
                    .build());

            userRepository.save(User
                    .builder()
                    .username("spring")
                    .password(passwordEncoder.encode("guru"))
                    .role(adminRole)
                    .build());

            log.info("Loaded authorities and users.");
        }

    }

}
