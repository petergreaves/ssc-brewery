package guru.sfg.brewery.security.google;

import com.warrenstrange.googleauth.ICredentialRepository;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class GoogleCredentialRepository implements ICredentialRepository {

    private final UserRepository userRepository;

    @Override
    public String getSecretKey(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getGoogle2faSecret();
    }

    @Override
    public void saveUserCredentials(String username, String secretKey, int validationCode, List<Integer> scratchCodes) {

        User user = userRepository.findByUsername(username).orElseThrow();
        user.setGoogle2faSecret(secretKey);
        user.setGoogle2faRequired(true);

        userRepository.save(user);


    }
}
