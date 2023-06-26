package com.example.pantryplanner;

public class GroceryHistory {
    private String groceryName;
    private String groceryIcon;
    private String statusChange;
    private String updateDate;

    public GroceryHistory(String groceryName, String groceryIcon, String statusChange, String updateDate) {
        this.groceryName = groceryName;
        this.groceryIcon = groceryIcon;
        this.statusChange = statusChange;
        this.updateDate = updateDate;
    }

    public String getGroceryName() {
        return groceryName;
    }

    public String getGroceryIcon() {
        return groceryIcon;
    }

    public String getStatusChange() {
        return statusChange;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setGroceryIcon(String ic_launcher) {
    }
}
