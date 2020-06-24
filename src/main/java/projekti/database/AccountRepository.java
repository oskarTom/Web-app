package projekti.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    Account findByUrl(String url);
    List<Account> findByNameIgnoreCase(String name);
}
