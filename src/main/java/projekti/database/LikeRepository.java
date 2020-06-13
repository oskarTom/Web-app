package projekti.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikePost, Long> {
    LikePost findByPostAndUser(Post post, Account user);
}
