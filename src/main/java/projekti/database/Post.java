package projekti.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Post extends AbstractPersistable<Long> {

    @ManyToOne
    private Account poster;
    private String content;
    private LocalDate date;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

}
