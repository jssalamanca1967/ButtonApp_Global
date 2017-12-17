package models;

import com.avaje.ebean.Finder;

import javax.persistence.*;

/**
 * Created by John on 10/06/2017.
 */
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    public long id;

    public String userName;
    public String password;
    public String token;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;

        // TODO Cambiar el generador
        // String generador =

        this.token = "abcd1234";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static Finder<Long, User> find = new Finder<Long,User>(User.class);
}
