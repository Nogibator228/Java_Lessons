package Threads;

class MyException extends Exception {
    private int detail;

    MyException(int a) {
        detail = a;
    }

    @Override
    public String toString() {
        return "MyException[" + detail + "]";
    }
}

/*
class NewThread extends Thread {    // Расширение класса thread, как один из способов создания потока.
    NewThread() {
        super("PotokIzClassa");
        start();
    }
    @Override
    public void run() {
        try {
            Thread t = Thread.currentThread();
            Thread.sleep(100);

            System.out.println("2 способ");
            System.out.println("**2** "+ t + " **2**");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
*/
class Callme {

    // Состояние гонок - одновременный вызов метода объекта несколькими потоками. Модификатор synchronized предоставляет монитор.
    // Нельзя в момент использования метода одним потоком, зайти в какой-либо другой синхронизированный метод того же объекта.
    void call(String msg) {
        System.out.print("[" + msg);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("LOOLKA");
        }
        System.out.println("]");
    }
}

class NewSubThread implements Runnable {    // Реализация интерфейса Runnable, как один из способов создания потока.
    Thread t;
    private String message;
    private final Callme target;

    NewSubThread(Callme targ, String msg) {
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

class Q {
    int n;
    private boolean valueSet = false;

    synchronized void get() {
        while (!valueSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("OKAAAY");
            }
        }
        System.out.println("Recieve: " + n);
        valueSet = false;
        notify();
    }

    synchronized void put(int n) throws InterruptedException {
        while (valueSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("OKAAAY");
            }
        }
        this.n = n;
        valueSet = true;
        Thread.sleep(500);
        System.out.println("Send: " + n);
        notify();
    }
}

class Producer implements Runnable {
    private Q q;
    Thread t;

    Producer(Q q) {
        this.q = q;
        t = new Thread(this, "Поставщик");
        t.start();
        // System.out.println(Thread.currentThread().getState());
    }

    @Override
    public void run() {
        int i = 0;
        while (i != 10) {
            try {
                q.put(i++);
            } catch (InterruptedException e) {
                System.out.println("LOL");
            }
        }
    }
}

class Consumer implements Runnable {
    private Q q;

    Consumer(Q q) {
        this.q = q;
        new Thread(this, "Потребитель").start();
    }

    @Override
    public void run() {
        while (q.n != 10) {
            q.get();
        }
    }
}

public class Shildt_Chapter_11 {
    private static void compute(int a) throws MyException {
        System.out.println("Вызван метод " + a);
        if (a > 10) throw new MyException(a);
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            compute(11);
        } catch (MyException e) {
            System.out.println("DNO " + e);
            e.initCause(new InterruptedException("NU TI NUBLO"));
            System.out.println(e.getCause());
        }

        Thread MainThread = Thread.currentThread(); // Ссылка на текущий поток
        MainThread.setName("Potok Glavar'");
        MainThread.setPriority(7);
        System.out.println(MainThread);

        Callme target = new Callme();
        // new NewThread(); // Создание нового потока через расширение класса.
        NewSubThread ob1 = new NewSubThread(target, "Первый поток"); // Создание нового потока через реализацию интерфейса.
        NewSubThread ob2 = new NewSubThread(target, "Второй поток");
        NewSubThread ob3 = new NewSubThread(target, "Третий поток");

        // System.out.println("Поток 1 запущен: " + ob1.t.isAlive());
        try {
            ob1.t.join();
            ob2.t.join();
            ob3.t.join(); // Потоки прекращают исполнение, после того как вызовы метода join() возвращают управление.
            //Thread.sleep(1000); //Фриз текущего потока
        } catch (InterruptedException e) {
            System.out.println("OH LOOOL");
        }
        Q q = new Q();
        Producer pot1 = new Producer(q);
        System.out.println(pot1.t.getState());
        new Consumer(q);

        /*
        System.out.println("Поток 1 запущен: " + ob1.t.isAlive());
        System.out.println("Поток 2 запущен: " + ob2.t.isAlive());
        System.out.println("Поток 3 запущен: " + ob3.t.isAlive());
        System.out.println("Главный поток завершен");*/
    }
}