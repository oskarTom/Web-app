package projekti.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PraiseRepository extends JpaRepository<Praise, Long> {
    List<Praise> findByUser(Account user);
}
