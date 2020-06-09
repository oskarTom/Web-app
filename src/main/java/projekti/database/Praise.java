package projekti.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Praise extends AbstractPersistable<Long> {
    @ManyToOne
    private Skill skill;
    @ManyToOne
    private Account user;
}
