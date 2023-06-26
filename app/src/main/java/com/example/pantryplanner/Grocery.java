package com.example.pantryplanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class Grocery {
    private int id;
    private String groceryType;
    private String groceryName;
    private String icon;
    private String store;
    private Date boughtDate;
    private Date openedDate;
    private Date useUntilDate;

    // constructor
    public Grocery(int id,String groceryType, String groceryName, String icon, String store,
                   String boughtDate, String openedDate, String useUntilDate) {
        this.id = id;
        this.groceryType = groceryType;
        this.groceryName = groceryName;
        this.icon = icon;
        this.store = store;
        setBoughtDate(GroceryHelper.stringToDateStatic(boughtDate));
        setOpenedDate(GroceryHelper.stringToDateStatic(openedDate));
        setUseUntilDate(GroceryHelper.stringToDateStatic(useUntilDate));
    }

    public Grocery(){

    }

    // getters

    public int getId() {
        return id;
    }
    public String getGroceryType() {
        return groceryType;
    }

    public String getGroceryName() {
        return groceryName;
    }

    public String getIcon() {
        return icon;
    }

    public String getStore() {
        return store;
    }

    public Date getBoughtDate() {
        return boughtDate;
    }

    public Date getOpenedDate() {
        return openedDate;
    }

    public Date getUseUntilDate() {
        return useUntilDate;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }
    public void setGroceryType(String groceryType) {
        this.groceryType = groceryType;
    }

    public void setGroceryName(String groceryName) {
        this.groceryName = groceryName;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setBoughtDate(Date boughtDate) {
        this.boughtDate = boughtDate;
    }

    public void setOpenedDate(Date openedDate) {
        this.openedDate = openedDate;
    }

    public void setUseUntilDate(Date useUntilDate) {
        this.useUntilDate = useUntilDate;
    }



    public boolean isAboutToExpire() {
        Date today = new Date();
        if (useUntilDate == null) {
            return false;
        }
        return today.after(useUntilDate);
    }

    public boolean isOpened() {
        Date today = new Date();
        if (openedDate == null) {
            return false;
        }
        return today.after(openedDate);
    }
}
