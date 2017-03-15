package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;


/**
 * Created by max on 13.03.17.
 */
@Entity
@Table(name = "Units")
public class Unit1 extends Model {
    @Id
    public static Long id;
    public String name;

    public static Finder<Long, Unit1> find = new Finder<Long, Unit1>(Unit1.class);

    public static List<Unit1> all() {
        return find.all();
    }

    public String getName() {
        return this.name;
    }
}
