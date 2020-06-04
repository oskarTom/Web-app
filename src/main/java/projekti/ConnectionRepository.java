package projekti;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    Connection findByUserAndFriend(Account user, Account friend);
    //Connection findByFriend(Account friend);
}
