package projekti.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    Connection findByUserAndFriend(Account user, Account friend);
    List<Connection> findByFriend(Account friend);
    List<Connection> findByUser(Account user);
}
