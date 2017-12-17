package controllers;

import models.Order;
import play.libs.Json;
import play.mvc.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/06/2017.
 */
public class OrderController extends Controller{

    /**
     * Version 1 of the Method.
     * @param table int indicating the number of the tableNumber
     * @param command String indicating the command for the tableNumber
     * @return
     */
    public Result receiveOrder(int table, String command){

        if(!command.isEmpty()){
            Order actualOrder = Order.find.where()
                    .eq("tableNumber", table)
                    .and()
                    .eq("isClosed", false)
                    .findUnique();
            if(actualOrder == null){
                if(command.equals(Order.CLOSE)){
                    return ok("The table " + table + " doesn't have open orders.");
                }
                else if(command.equals(Order.BILL) || command.equals(Order.MENU) || command.equals(Order.SERVICE)){
                    Order newOrder = new Order(table, command);
                    newOrder.save();

                    return ok("Order created successfully");
                }
                else{
                    return ok("Try a valid command");
                }

            }
            else{
                if(command.equals(Order.CLOSE)){
                    actualOrder.closeCommand();
                    actualOrder.save();

                    return ok("The table " + table + " closed it's order succesfully");
                }
                else if(command.equals(Order.BILL) || command.equals(Order.MENU) || command.equals(Order.SERVICE)){
                    actualOrder.countCommand(command);
                    actualOrder.save();

                    return ok("The table " + table + " already has an open order");
                }
                else{
                    return ok("Try a valid command");
                }
            }
        }
        else{
            return ok("Try again with a command");
        }
    }

    public Result jsonOrders(){
        List<Order> orders = orders();
        List<JSONResponse> jsonResponses = new ArrayList<>();

        for(Order order: orders){
            jsonResponses.add(new JSONResponse(order));
        }

        return ok(Json.toJson(jsonResponses));
    }

    public Result jsonOrdersCancelAll(){
        List<Order> orders = orders();

        for(Order order: orders){
            order.closeCommand();
            order.save();
        }
        return ok("All orders have been cancelled");
    }

    public Result cancelAll(){
        List<Order> orders = orders();

        for(Order order: orders){
            order.closeCommand();
            order.save();
        }
        return redirect(routes.HomeController.index());
    }

    public Result refreshTable(){
        List<Order> orders = orders();
        return ok(views.html.order.orders.render(orders));

    }

    public Result closeManually(int table){
        Order actualOrder = Order.find.where()
                .eq("tableNumber", table)
                .and()
                .eq("isClosed", false)
                .findUnique();
        actualOrder.closeCommand();
        actualOrder.save();

        return redirect(routes.OrderController.refreshTable());
    }

    private List<Order> orders(){
        List<Order> orders = Order.find.where()
                .eq("isClosed", false)
                .order("initialTime ASC")
                .findList();

        return orders;
    }

    private class JSONResponse {
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
         * Time that has passed between the creation of the order and now
         */
        public Long timeUntilNow;

        public JSONResponse(Order order) {
            this.id = order.id;
            this.tableNumber = order.tableNumber;
            this.command = order.command;
            this.initialTime = order.initialTime;
            this.timeUntilNow = System.currentTimeMillis() - initialTime;
        }
    }

}
