package models;


import com.avaje.ebean.Finder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/06/2017.
 */
@Entity
@Table(name="restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    public long id;

    public String name;

    @ManyToOne
    public Country country;

    @ManyToOne
    public City city;
    public String address;

    @ManyToMany
    public List<Order> orders;

    public Restaurant(String name, Country country, City city, String address) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.address = address;
        this.orders = new ArrayList<>();
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public void addOrders(List<Order> orders){
        orders.addAll(orders);
    }

    public static Finder<Long, Restaurant> find = new Finder<Long,Restaurant>(Restaurant.class);

}
