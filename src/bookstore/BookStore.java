/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import model.Book;
import model.BookAdditional;
import model.BookGenre;
import model.Customer;
import model.Order;
import model.Profit;
import model.Seller;

/**
 * Покупатель заказывает книги у продавца
 * 1. Покупатель +
 * 2. Книги +
 * 3. Продавец +
 * 4. Заказы +
 * 
 * ordder.add(new Order{5,3,4,new long[]{2,5,9}));
 * Заказ №5 Продавец №3 продал Покупателю №4 книги под номерами: 2,5,9
 * 
 * Задачи программы:
 * 1) Получить общее количество проданных книг и общую стимость всех этих книг
 * 2) Получить количество и стоимость проданных книг по продавцам
 * 3) Количество и общую стоимость по жанрам
 * 4) Какой жанр популярен среди покупателей возрастом до 30 лет?
 * 5) Какой жанр популярен среди покупателей возрастом старше 30 лет?
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
        
        //инициализируем данные
        initData();
        
        // задача №1
        System.out.println("Задача №1");
        String info = String.format("Общее кол-во проданных книг %d на сумму %f", getCountOfSoldBooks(),getAllPriceOfSoldBooks());
        System.out.println(info);
        System.out.println("");
        
        // задача №2
        System.out.println("Задача №2");
        for (Seller seller : sellers) {
            System.out.println(seller.getName() + " продал(а) " + getProfitBySeller(seller.getId()).toString());
        }
        System.out.println("");
        
        // задача №3
        System.out.println("Задача №3");
        ArrayList<BookAdditional> soldBooksCount = getCOuntOfSoldBooksByGenre();
        HashMap<BookGenre,Double> soldBookPrices = getPriceOfSOldBooksByGenre();
        
        String soldBookStr = "По жанру: %s продано %d книг общей стоимостью %f";
        for (BookAdditional bookAdditional : soldBooksCount) {
            double price = soldBookPrices.get(bookAdditional.getGenre());
            System.out.println(String.format(soldBookStr, bookAdditional.getGenre().name(),bookAdditional.getCount(),price));  
        }
        System.out.println("");
        
        // задача №4
        System.out.println("Задача №4");
        int age = 30;
        String analyzeGenreStr = "Покупатели до %d лет выбирают жанр %s";
        System.out.println(String.format(analyzeGenreStr, 30, getMostPopularGenreLessThenAge(30)));
        System.out.println("");
        
        // задача №5
        System.out.println("Задача №5");
        String analyzeGenreStr2 = "Покупатели страше %d лет выбирают жанр %s";
        System.out.println(String.format(analyzeGenreStr2, 30, getMostPopularGenreMoreThenAge(30)));
    }
    
    //***********Решение**************4-5
    /**
     * Получить наиболле популярный жанр для покудателей до возраста #age
     * @param age требуемый возраст
     * @return популярный жанр
     */
    public static BookGenre getMostPopularGenreLessThenAge(int age) {
        ArrayList<Long> customersIds = new ArrayList<>();
        
        for (Customer customer : customers) {
            if (customer.getAge() < age) {
                customersIds.add(customer.getId());
            }
        }
        return getMostPopularBookGenre(customersIds);
    }
    
    /**
     * Получить наиболле популярный жанр для покудателей старше возраста #age
     * @param age требуемый возраст
     * @return популярный жанр
     */
    public static BookGenre getMostPopularGenreMoreThenAge(int age) {
        ArrayList<Long> customersIds = new ArrayList<>();
        
        for (Customer customer : customers) {
            if (customer.getAge() > age) {
                customersIds.add(customer.getId());
            }
        }
        return getMostPopularBookGenre(customersIds);
    }

    private static BookGenre getMostPopularBookGenre(ArrayList<Long> customersIds) {
        int countArt = 0, countPr = 0, countPs = 0;
        for (Order order : orders) {
            if (customersIds.contains(order.getCustomerId())) {
                countArt += getCountOfSoldBooksByGenre(order, BookGenre.Art);
                countPr += getCountOfSoldBooksByGenre(order, BookGenre.Programming);
                countPs += getCountOfSoldBooksByGenre(order, BookGenre.Psychology);
            }
        }
        ArrayList<BookAdditional> result = new ArrayList<>();
        result.add(new BookAdditional(BookGenre.Art,countArt));
        result.add(new BookAdditional(BookGenre.Psychology,countPs));
        result.add(new BookAdditional(BookGenre.Programming,countPr));
        result.sort(new Comparator<BookAdditional>() {
            @Override
            public int compare(BookAdditional left, BookAdditional right) {
                return right.getCount() - left.getCount();
            }
        });
        return result.get(0).getGenre();
    }
    
    //------------Решение--------------3
    //Получить количество проданных книг по жанрам
    public static ArrayList<BookAdditional> getCOuntOfSoldBooksByGenre() {
        ArrayList<BookAdditional> result = new ArrayList<>();
        int countArt = 0, countPr = 0, countPs = 0;
        
        for (Order order : orders) {
            countArt += getCountOfSoldBooksByGenre(order, BookGenre.Art);
            countPs += getCountOfSoldBooksByGenre(order, BookGenre.Psychology);
            countPr += getCountOfSoldBooksByGenre(order, BookGenre.Programming);
        }
        
        result.add(new BookAdditional(BookGenre.Art,countArt));
        result.add(new BookAdditional(BookGenre.Psychology,countPs));
        result.add(new BookAdditional(BookGenre.Programming,countPr));
        
        return result;
    }
    
     /**
     *Получить количество книг в одном заказе по определённому жанру
     * @param order заказ
     * @param genre жанр
     * @return количество книг
     */
    public static int getCountOfSoldBooksByGenre(Order order, BookGenre genre) {
        int count = 0;
        
        for (long bookId : order.getBooks()) {
            Book book = getBookById(bookId);
            if (book != null && book.getGenre() == genre)
                count++;
            
        }
        return count;
    }
    
    //возвращаемый результат hashmap //получить стоимость проданных книг по жанрам
    public static HashMap<BookGenre, Double> getPriceOfSOldBooksByGenre() {
        HashMap<BookGenre,Double> result = new HashMap<>();
        double priceArt = 0, pricePr = 0, pricePs = 0;
        
        for (Order order : orders) {
            priceArt += getPriceOfSoldBooksByGenre(order, BookGenre.Art);
            pricePr += getPriceOfSoldBooksByGenre(order, BookGenre.Programming);
            pricePs += getPriceOfSoldBooksByGenre(order, BookGenre.Psychology);
        }
        result.put(BookGenre.Art, priceArt);
        result.put(BookGenre.Programming, pricePr);
        result.put(BookGenre.Psychology, pricePs);
        
        return result;
    }
    
    /**
     *Получить общую сумму книг в одном заказе по определённому жанру
     * @param order заказ
     * @param genre жанр
     * @return количество и общую стоимость указанного продавца
     */
    public static double getPriceOfSoldBooksByGenre(Order order, BookGenre genre) {
        double price = 0;
        
        for (long bookId : order.getBooks()) {
            Book book = getBookById(bookId);
            if (book != null && book.getGenre() == genre)
                price += book.getPrice();
            
        }
        return price;
    }
    
   
    //****************Решение**********************2
    /**
     *Получить общее количество и общую стоимость проданного товара для продавца
     * @param sellerId уникальный номер продавца
     * @return количество и общую стоимость указанного продавца
     */
    public static Profit getProfitBySeller(long sellerId) {
        int count = 0; double price = 0;
        for (Order order : orders) { //просматриваем каждый заказ
           if (order.getSellerId() == sellerId) {
               price += getPriceOfSoldBooksInOrder(order);
               count += order.getBooks().length;
           }
        }
        
        return new Profit(count,price);  //анонимная переменная, одноразовая
    }
    //***********Решение************1
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
    * @param order заказ по которому ситаеться стоимость
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
    
   //_________________________DATA____________________________________ 
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
        books.add(new Book( 7, "Как перестать беспокоиться и начать жить", "Дейл Карнеги", 1000 , BookGenre.Psychology));
        
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
