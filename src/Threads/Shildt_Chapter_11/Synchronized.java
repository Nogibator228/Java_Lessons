package Threads.Shildt_Chapter_11;

// Состояние гонок - одновременный вызов метода объекта несколькими потоками. Модификатор synchronized предоставляет монитор.
// Нельзя в момент использования метода одним потоком, зайти в какой-либо другой синхронизированный метод того же объекта.
class Callme {
    void call(String msg) {
        System.out.print("[Вызван был: " + msg);
        try {
             Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("СОВСЕМ ПОШЛО ВСЕ НЕ ТАК, ОДИН ИЗ N ПОТОКОВ ПЕРЕКОЧЕВРЯЖИЛСЯ");
        }
        System.out.println("]");
    }
}

// Расширение класса thread, как один из способов создания потока.
class Thread_From_Class extends Thread {
    private final Callme target;
    private String inputStr;

    Thread_From_Class(Callme targ, String str) {
        super(str);
        target = targ;
        inputStr = str;
        start();
    }

    @Override
    public void run() {
        synchronized (target) {
            target.call(inputStr);
        }
    }
}

// Реализация интерфейса Runnable, как один из способов создания потока.
class Thread_From_Interface implements Runnable {
    Thread t;
    private String message;
    private final Callme target;

    Thread_From_Interface(Callme targ, String msg) {
        target = targ;
        message = msg;
        t = new Thread(this);  // Thread(Runnable объект, String имя_потока).
        t.start(); // Запуск Run() и продолжение действа в вызвавшем потоке в параллель (main()).
    }

    @Override
    public void run() { // Поток завершится, когда метод Run() возвратит управление.
        synchronized (target) {
            target.call(message);
        }
    }
}

class MyException extends Exception {
    private String detail;

    MyException(String a) {
        detail = a;
    }

    @Override
    public String toString() {
        return "MyException[" + detail + "]";
    }
}

public class Synchronized {
    public static void main(String[] args) throws InterruptedException, MyException {
        try {
            Thread MainThread = Thread.currentThread(); // Ссылка на текущий поток
            MainThread.setName("Potok Glavar'");
            MainThread.setPriority(7);
            System.out.println("Текущий поток: " + MainThread);
            throw new MyException("А теперь, вызову-ка я, исключение свое");
        } catch (MyException e) {
            e.initCause(new InterruptedException("Остановка потока (якобы :) )")); // Инициализация исключения - причины моего исключения.
            System.out.println("Причина исключения: " + e + " являетя - " + e.getCause()); // Вывод причины моего исключения.
        }

        Callme target = new Callme();
        Thread_From_Interface ob1 = new Thread_From_Interface(target, "Первый поток"); // Создание нового потока через реализацию интерфейса.
        Thread_From_Interface ob2 = new Thread_From_Interface(target, "Второй поток");
        Thread_From_Interface ob3 = new Thread_From_Interface(target, "Третий поток");
        Thread_From_Class ob4 = new Thread_From_Class(target, "Четвертый поток");     // Создание нового потока через расширение класса.

        // Потоки прекращают исполнение, после того как вызовы метода join() возвращают управление.
        try {
            ob1.t.join();
            ob2.t.join();
            ob3.t.join();
            ob4.join();
        } catch (InterruptedException e) {
            System.out.println("OH LOOOL");
        }

        System.out.println("Поток 1 запущен: " + ob1.t.isAlive());
        System.out.println("Поток 2 запущен: " + ob2.t.isAlive());
        System.out.println("Поток 3 запущен: " + ob3.t.isAlive());
        System.out.println("Поток 4 запущен: " + ob4.isAlive());
        System.out.println("Главный поток завершен");
    }
}