package Threads.Shildt_Chapter_11;

class Methods {
    int n;
    private boolean valueSet = false;

    synchronized void get() {
        if (!valueSet) {
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

    synchronized void put(int k) throws InterruptedException {
        if (valueSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("OKAAAY");
            }
        }
        n = k;
        valueSet = true;
        Thread.sleep(200);
        System.out.println("Send: " + n);
        notify();
    }
}

class Producer implements Runnable {
    private Methods methods;
    Thread t;

    Producer(Methods methods) {
        this.methods = methods;
        t = new Thread(this, "Поставщик");
        t.start();
    }

    @Override
    public void run() {
        int k = 0;
        while (methods.n < 10) {
            try {
                methods.put(++k);
            } catch (InterruptedException e) {
                System.out.println("LOOOOLKA");
            }
        }
    }
}

class Consumer implements Runnable {
    private Methods methods;
    Thread t;

    Consumer(Methods methods) {
        this.methods = methods;
        t = new Thread(this, "Потребитель");
        t.start();
    }

    @Override
    public void run() {
        while (methods.n < 10) {
            methods.get();
        }
    }
}

public class Consistency {
    public static void main(String[] args) {
        Methods methods = new Methods(); // Задание методологии операций.

        Producer pot1 = new Producer(methods);
        Consumer prod1 = new Consumer(methods);

        System.out.println(pot1.t.getState());
        System.out.println(prod1.t.getState());
    }
}