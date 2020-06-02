package projekti;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

@Entity
@Data
public class Connection extends AbstractPersistable<Long> {

}
