package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modle.Item;
import view.Tm.ItemTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemAdminController {
    public static ObservableList<ItemTm> loadItems() throws SQLException, ClassNotFoundException {
        ObservableList<ItemTm> obList= FXCollections.observableArrayList();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM item").executeQuery();
        while (rst.next()){
            obList.add(new ItemTm(
                    rst.getString(1), rst.getString(2),Double.valueOf(rst.getString(3)),Integer.valueOf(rst.getString(4))
            ));
        }
        return obList;
    }

    public static boolean addItem(ArrayList<Item> items, String itemId) throws SQLException, ClassNotFoundException {
        if (!checkItem(itemId)) {
            return false;
        }

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
        for (Item temp:items
        ) {
            stm.setString(1,temp.getItemId());
            stm.setString(2,temp.getItemName());
            stm.setDouble(3,temp.getUnitePrice());
            stm.setInt(4,temp.getQtyOnHand());
        }
        return stm.executeUpdate()>0;
    }

    public static ArrayList<String> loadItemCode() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT item_Id FROM item").executeQuery();
        ArrayList<String> temp=new ArrayList<>();
        while (rst.next()) {
            temp.add(rst.getString(1));
        }
        return temp;
    }

    public static boolean updateItem(ArrayList<Item> itemData) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE item SET Item_Name=?,item_Price=?,qty_On_Hand=? WHERE item_Id=?");
        for (Item temp:itemData
        ) {
            stm.setString(1,temp.getItemName());
            stm.setDouble(2,temp.getUnitePrice());
            stm.setInt(3,temp.getQtyOnHand());
            stm.setString(4,temp.getItemId());

            return stm.executeUpdate()>0;
        }
        return false;
    }

    public static ArrayList<Item> searchItem(Object itemId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM item WHERE item_Id=?");
        stm.setObject(1,itemId);
        ResultSet rst = stm.executeQuery();
        ArrayList<Item> temp=new ArrayList();
        if (rst.next()) {
            temp.add(new Item(
                    rst.getString(1), rst.getString(2), rst.getDouble(3), rst.getInt(4)
            ));
        }
        return temp;
    }

    public static boolean deleteItem(Object itemId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM item WHERE item_Id=?");
        stm.setObject(1,itemId);
        return stm.executeUpdate()>0;
    }

    private static boolean checkItem(String itemId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM item WHERE item_Id=?");
        stm.setString(1,itemId);
        if (stm.executeQuery().next()) {
            return false;
        }
        return true;
    }
}
