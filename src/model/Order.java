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
public class Order {
    private long id; //уникальный номер
    private long sellerId; // id продавца
    private long customerId; // id покупателя
    private long[] books; // список номеров книг которые продали

    public Order() {
    }

    public Order(long id, long sellerId, long customerId, long[] books) {
        this.id = id;
        this.sellerId = sellerId;
        this.customerId = customerId;
        this.books = books;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long[] getBooks() {
        return books;
    }

    public void setBooks(long[] books) {
        this.books = books;
    }
    
}
