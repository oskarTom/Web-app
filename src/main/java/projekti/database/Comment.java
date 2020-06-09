package projekti.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Comment extends AbstractPersistable<Long> {

    @ManyToOne
    private Post post;
    @ManyToOne
    private Account poster;
    private String content;

}
