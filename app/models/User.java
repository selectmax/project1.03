package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by max on 01.03.17.
 */

@Entity
@Table(name="Users")

public class User extends Model {

    @Id
    public Long id;
    @Constraints.Required
    @Formats.NonEmpty
    @Column(unique = true)
    public String login;

    @Constraints.Required
    @Formats.NonEmpty
    public String password;

    public static Finder<Long, User> find = new Finder<Long, User>(User.class);

    public static User authenticate(String login, String clearPassword) throws Exception {
        User user = find.where().eq("login", login).findUnique();
        if (user != null) {
            if (clearPassword.equals(user.password)) {
                return user;
            }
        }
        return null;
    }

    public static User findBylogin(String login) {
        return find.where().eq("login", login).findUnique();
    }
}
