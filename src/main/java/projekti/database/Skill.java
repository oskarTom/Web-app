package projekti.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Skill extends AbstractPersistable<Long> {
    @ManyToOne
    private Account user;
    private String content;
    @OneToMany(mappedBy = "skill")
    private List<Praise> praises;
}
