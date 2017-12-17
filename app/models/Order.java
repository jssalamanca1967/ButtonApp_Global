package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by John on 8/06/2017.
 */
@Entity
@Table(name="tableorder")
public class Order extends Model {

    public final static String BILL = "bill";
    public final static String SERVICE = "service";
    public final static String MENU = "menu";
    public final static String CLOSE = "close";

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    public Long id;

    /**
     * Table number
     */
    public int tableNumber;

    /**
     * Command received
     */
    public String command;

    /**
     * Initial time when the order was first generated
     */
    public Long initialTime;

    /**
     * Time when the order was canceled and closed
     */
    public Long finalTime;

    /**
     * Total time between the generation and closing of the order
     */
    public Long serviceTime;

    /**
     * Indicates if the order is actually closed
     */
    public boolean isClosed;

    /**
     * Indicates how many times the Bill Button was pressed during the actual order
     */
    public int billCount;

    /**
     * Indicates how many times the Service Button was pressed during the actual order
     */
    public int serviceCount;

    /**
     * Indicates how many times the Menu Button was pressed during the actual order
     */
    public int menuCount;

    /**
     * Indicates how many times the Close Button was pressed during the actual order
     */
    public int closeCount;

    /**
     * Creates a new order
     * <b>pos: </b>A new order was created
     * @param tableNumber int indicating the tableNumber number
     * @param command String indicating the command received
     */
    public Order(int tableNumber, String command) {
        this.tableNumber = tableNumber;
        this.command = command;
        this.initialTime = System.currentTimeMillis();
        this.isClosed = false;
        this.billCount = 0;
        this.menuCount = 0;
        this.serviceCount = 0;
        this.closeCount = 0;
        if(command.equals(BILL))
            this.billCount = 1;
        else if(command.equals(SERVICE))
            this.serviceCount = 1;
        else if(command.equals(MENU))
            this.menuCount = 1;
    }

    /**
     * Updates the order to close it
     */
    public void closeCommand(){
        this.finalTime = System.currentTimeMillis();
        this.serviceTime = finalTime - initialTime;
        this.isClosed = true;
        this.closeCount = 1;
    }

    public void countCommand(String command){
        if(!this.command.equals(command)){
            this.command = SERVICE;
        }
        if(command.equals(BILL)) {
            this.billCount++;
        }
        else if(command.equals(SERVICE)) {
            this.serviceCount++;
        }
        else if(command.equals(MENU)) {
            this.menuCount++;
        }
    }

    public static Finder<Long, Order> find = new Finder<Long,Order>(Order.class);
}
