package controller;

import db.DbConnection;
import modle.Item;
import modle.Order;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ItemController {

    public static String setOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT order_Id FROM `order` order by order_Id DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempId=Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "Order-00"+tempId;
            }else if (tempId<=99){
                return "Order-0"+tempId;
            }else {
                return "Order-"+tempId;
            }
        }else {
            return "Order-001";
        }
    }

    public static ArrayList<Item> getItem(String itemId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM item WHERE item_Id=?");
        stm.setString(1,itemId);
        ResultSet rst = stm.executeQuery();
        ArrayList<Item> tempItem=new ArrayList();
        if (rst.next()) {
            tempItem.add(new Item(
                    rst.getString(1), rst.getString(2), rst.getDouble(3), rst.getInt(4)
            ));
        }
        return tempItem;
    }

    public static String[][] getApoAmount(String apoNo) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT price,service_Id FROM appointment WHERE apo_No=?");
        stm.setString(1,apoNo);
        ResultSet rst = stm.executeQuery();
        String[][] apoData=new String[1][2];
        if (rst.next()) {
            apoData[0][0]=String.valueOf(rst.getDouble(1));
            apoData[0][1]= rst.getString(2);
        }
        return apoData;
    }

    public static boolean placeOrder(ArrayList<Item> item, Order order) throws SQLException, ClassNotFoundException {
        Connection con=null;
        try {
            con=DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO `order` VALUES (?,?,?,?,?)");
            stm.setString(1,order.getOrderId());
            stm.setString(2,order.getApoNo());
            stm.setString(3,order.getCustomerId());
            stm.setDouble(4,order.getTotal());
            stm.setObject(5, order.getDate());
            if (stm.executeUpdate()>0) {
                System.out.println("----------------------");
                if (saveOrderDetails(item,order.getOrderId())){
                    con.commit();
                    return true;
                }else {
                    con.rollback();
                    return false;
                }
            }else {
                System.out.println("========");
                con.rollback();
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    private static boolean saveOrderDetails(ArrayList<Item> item,String orderId) throws SQLException, ClassNotFoundException {
        for (Item tm:item
             ) {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO order_detail VALUES (?,?,?)");
            stm.setString(1,orderId);
            stm.setString(2, tm.getItemId());
            stm.setInt(3,tm.getQtyOnHand());

            if (stm.executeUpdate()>0) {
                if (updateQtyOnHandItem(tm.getItemId(),tm.getQtyOnHand())){

                }else {

                    return false;
                }
            }else {
                return false;
            }
        }

        return true;
    }

    private static boolean updateQtyOnHandItem(String itemId, int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE item set qty_On_Hand=(qty_On_Hand-"+qty+") where item_Id='"+itemId+"'");
       // System.out.println(stm.executeUpdate()>0);
        return stm.executeUpdate()>0;
    }

    public static ArrayList<String> loadItems() throws SQLException, ClassNotFoundException {
        ArrayList<String> itemCode=new ArrayList();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM item").executeQuery();
        while (rst.next()){
            itemCode.add(
                    rst.getString(1)
            );
        }
        return itemCode;
    }

    public static ArrayList<String> loadApoId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM appointment").executeQuery();
        ArrayList<String> temp=new ArrayList();
        while (rst.next()){
            temp.add(rst.getString(1));
        }
        return temp;
    }


}
