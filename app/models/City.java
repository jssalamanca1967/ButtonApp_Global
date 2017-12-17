package models;

import com.avaje.ebean.Finder;

import javax.persistence.*;

/**
 * Created by John on 10/06/2017.
 */
@Entity
@Table(name="city")
public class City {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    public long id;

    private String name;

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Finder<Long, City> find = new Finder<Long,City>(City.class);
}
