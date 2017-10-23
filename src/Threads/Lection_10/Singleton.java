package Threads.Lection_10;

// Единственный экземпляр некого класса на всю программу.
public class Singleton {
    private int foo;
    private String bar;

    private Singleton() {
        foo = 13;
        bar = "zap";
    }
    // эти записи, присовения полей экземпляра foo = 13; bar = "zap";, и присвоение значения статической переменной instance = new Singleton(); они могут быть произвольным образом переупорядочены.
    // может выйти так: мы уже записали в поле instance ссылку на новый объект, но при этом его значения полей еще не инициализированы
    // и выйдет что ссылка на недостроенный объект утекла в программу. (состояние объекта некорректно)
    // рекомендуется не использовать DCL, если конечно не стоит слово volatile - он нам обеспечивает happens-before, между записью в volatile переменную
    // ссылки на объект instance = new Singleton(), мы знаем, что присвоение полей должны были произойти до нее, до записи этой ссылки в объект.
    // здесь:if (instance == null) { // идиома: ..., чтение из volatile переменной, и соответственно гарантированно видтим все то что в памяти происходило в месте instance = new Singleton();
    // получить bottle neck на синхронизации нужно еще постараться, и поэтому, можно ограничиться лишь synchronized
    private static volatile Singleton instance;
    // Эта идиома некорректна, нужно понимать, что инициализация instance = new Singleton() - не только присвоение ссылки на новый оъект, но это еще и присвоение значений полей, создаваемого экземпляра.
    public static Singleton getInstance() {
        if (instance == null) { // идиома: anti pattern - double check locking, двойная проверка и lock
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
