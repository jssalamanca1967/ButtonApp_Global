package models;

import com.avaje.ebean.Finder;

import javax.persistence.*;

/**
 * Created by John on 10/06/2017.
 */
@Entity
@Table(name="country")
public class Country {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    public long id;

    private String name;

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Finder<Long, Country> find = new Finder<Long,Country>(Country.class);

}
