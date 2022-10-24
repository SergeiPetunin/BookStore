/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import java.util.ArrayList;
import model.Book;
import model.BookGenre;
import model.Customer;
import model.Order;
import model.Seller;

/**
 *
 * @author spetu
 */
public class BookStore {

    /**
     * @param args the command line arguments
     */
    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<Seller> sellers = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();
    
    public static void main(String[] args) {
        // TODO code application logic here
        initData();
        
        String info = String.format("Общее кол-во проданных книг %d на сумму %f", getCountOfSoldBooks(),getAllPriceOfSoldBooks());
        System.out.println(info);
    }
    
    
    //получить общую сумму заказов
    public static double getAllPriceOfSoldBooks() {
        double price = 0;
        for (Order order : orders) {
            price += getPriceOfSoldBooksInOrder(order);
        }
        return price;
    }
    
    /**
    * Получить общую стоимость одного заказа
    * @param order заказ по которому чситаеться стоимость
    * @return общяя стоимтоть для всех проданных книг в заказе
    */
    public static double getPriceOfSoldBooksInOrder(Order order) {
        double price = 0;
        
        for (long bookId : order.getBooks()) {
            Book book = getBookById(bookId);
            if (book != null)
                price += book.getPrice();
            
        }
        return price;
    }
    
    /**
    * Получить общее количество проданных книг
    * @return общее количество книг
    */
    public static int getCountOfSoldBooks() {
        int count = 0;
        for (Order order : orders) {
            count += order.getBooks().length;
        }
        return count;
    }
    
    /**
    * Поиск книжки в списке книг по её уникальному номеру
    * @param id уникальный номер книжки
    * @return найденная книжка
    */
    public static Book getBookById(long id) {
        Book current = null; // обьявим пустую книгу
        
        for (Book book : books) { //проходим по списку книг
           if (book.getId() == id) {
              current = book;
              break;
           } 
        }
        
        return current;
    }
    
    
    public static void initData() {
        
        // продавцы
        sellers.add(new Seller( 1, "Иван Иванов", 32 ));
        sellers.add(new Seller( 2, "Петр Петров", 30 ));
        sellers.add(new Seller( 3, "Алиса Васильева", 26 ));
        
        //покупатели
        customers.add(new Customer( 1, "Алексоей Сидоров", 25 ));
        customers.add(new Customer( 2, "Дмитрий Романов", 32 ));
        customers.add(new Customer( 3, "Кирилл Сидоров", 19 ));
        customers.add(new Customer( 4, "Данил Кириенко", 45 ));
        customers.add(new Customer( 5, "Воротынцева Элина", 23 ));
        
        //книги
        books.add(new Book( 1, "Вона и мир", "Лев Толстой", 1600, BookGenre.Art));
        books.add(new Book( 2, "Преступление и наказание", "Федор Достоевский", 600, BookGenre.Art));
        books.add(new Book( 3, "Мертвые Души", "Николай Гоголь", 750, BookGenre.Art));
        books.add(new Book( 4, "Руслан и Людмила", "Алекстандр Пушкин", 500, BookGenre.Art));
        
        books.add(new Book( 5, "Введение в психоанализ", "Зигмунд Фрейд", 1050, BookGenre.Psychology));
        books.add(new Book( 6, "Психология виляния. Воздействуй. Защищайся", "Роберт Чалдини", 550, BookGenre.Psychology));
        books.add(new Book( 7, "Как перестать беспокоиться и начать жить", "Дейл Карнеги", 1000, BookGenre.Psychology));
        
        books.add(new Book( 8, "Gang Of Four", "Эрих Гамма", 900, BookGenre.Programming));
        books.add(new Book( 9, "Совершенный код", "Стив Макконнел", 1200, BookGenre.Programming));
        books.add(new Book( 10, "Рефокторинг. Улучшение существующего кода", "Фаулер Мартин", 850, BookGenre.Programming));
        books.add(new Book( 11, "Алгоритмы. Посторение и анализ", "Томас Кормен", 450, BookGenre.Programming));
        
        //заказы
        orders.add(new Order( 1, 1, 1, new long[]{8,9,10,11}));
        orders.add(new Order( 2, 1, 2, new long[]{1}));
        orders.add(new Order( 3, 2, 3, new long[]{5,6,7}));
        orders.add(new Order( 4, 2, 4, new long[]{1,2,3,4}));
        orders.add(new Order( 5, 3, 5, new long[]{2,5,9}));
    }
    
}
