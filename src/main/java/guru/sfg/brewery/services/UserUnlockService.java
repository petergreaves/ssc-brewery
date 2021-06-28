package guru.sfg.brewery.services;

import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserUnlockService {

    private final UserRepository userRepository;

    @Scheduled(fixedRate = 5000)
    public void unlockAccounts() {

        log.debug("Running unlock accounts service ... ");
        List<User> unlockUsers = userRepository.findAllByAccountNonLockedAndLastModifiedDateBefore(false,
                Timestamp.valueOf(LocalDateTime.now().minusSeconds(30)));

        if (unlockUsers.size() > 0) {
            unlockUsers.forEach(u -> {

                u.setAccountNonLocked(true);
                log.debug("Unlocking {}",u.getUsername());
            });
            userRepository.saveAll(unlockUsers);

        }
        log.debug("Unlocked {} account(s) ... ", unlockUsers.size());
    }
}
