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
public class BookAdditional {
    private BookGenre genre;
    private int count;

    public BookAdditional() {
    }

    public BookAdditional(BookGenre genre, int count) {
        this.genre = genre;
        this.count = count;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BookAdditional{" + "genre=" + genre + ", count=" + count + '}';
    }
    
    
}
