package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by max on 15.03.17.
 */
@Entity
public class Unit1 {
    @Id
    public Long id;
    public String name;

    public static Model.Finder<Long, Unit1> find = new Model.Finder<Long, Unit1>(Unit1.class);

    public static List<Unit1> all(){
        return find.all();
    }

    public String getName() {
        return name;
    }


}
