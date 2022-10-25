/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author spetu
 */
public class Profit {
    private int cout; // количесчтво проданного товара
    private double price; //общаа цена

    public Profit() {
    }

    public Profit(int cout, double price) {
        this.cout = cout;
        this.price = price;
    }

    public int getCout() {
        return cout;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return " всего "  + cout + " книг(у) на сумму: " + price;
    }
    
    
}
