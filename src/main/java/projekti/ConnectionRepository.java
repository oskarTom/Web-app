package projekti;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    Connection findByUser(Account user);
    Connection findByFriend(Account friend);
}
