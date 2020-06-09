package projekti.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {

    @NotEmpty
    @Size(min = 3, max = 15)
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 30)
    private String url;


    @OneToMany(mappedBy = "user")
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Praise> praises = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Connection> connections = new ArrayList<>();

    @OneToMany(mappedBy = "poster")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "poster")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

}