package models;


import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by max on 01.03.17.
 */
@Entity
@Table(name="orders")
public class Order extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public long id;
    @Constraints.Required
    public String name;
    @Constraints.Required
    public int count;
    @Constraints.Required
    public String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UnitID", nullable = false)
    public Unit1 unit;

    //1public String client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClientID", nullable = false)
    public User client;

    public Date time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id  = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name  = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Finder<Long, Order> find = new Finder<Long,Order>(Order.class);

    @Override
    public String toString(){
        return name;
    }


}

