package Threads.Lection_10;

import java.util.Arrays;

public class Creating_Threads {
    public static void main(String[] args) {
        // 1) Создание потока как экземпляр анонимного класса
        Thread threadClass = new Thread() {
            @Override
            public void run() {
                System.out.println("Я в потоке от анонимного класса типа Thread (№1)!");
            }
        };

        // 2) Или через лямбду (от IDE):
        Thread threadLyambdaClass = new Thread(() -> System.out.println("Я в потоке от анонимного класса типа Thread, через лямбду созданного! (№2)"));

        // 3) Создание потока через отсылку конструктору Thread объекта, реализующего интерфейс Runnable
        Runnable runnableThread = new Runnable() {
            @Override
            public void run() {
                System.out.println("Я в потоке от анонимного класса реализующего интерфейс (№3)!");
            }
        };
        Thread threadInterface = new Thread(runnableThread);

        // 4) Создание потока как эквивалент - лямбдой, через интерфейс Runnable.
        Runnable LyambdaInterface = () -> System.out.println("Я в потоке от анонимного класса реализующего интерфейс, через лямбду созданного (№4)");
        Thread threadLyambdaInterface = new Thread(LyambdaInterface);

        threadClass.start();
        threadLyambdaClass.start();
        threadInterface.start();
        threadLyambdaInterface.start();

        try {
            threadClass.join();
            threadLyambdaClass.join();
            threadInterface.join();
            threadLyambdaInterface.join();
        } catch (InterruptedException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        System.out.println(" ");
        // Пример из презентации лекции для создания потоков
        HelloRunnable helloRunnable = new HelloRunnable(); // Есть ли у объекта состояние? Что будет если 10 потоков будут одновременно работать с этим состоянием?
        for (int i = 0; i < 10; ++i) {
            new HelloWorld().start();
            new Thread(new HelloRunnable()).start(); // .run от Thread не запустить бы :)
            new Thread(() -> System.out.println("Hello from " + Thread.currentThread().getName())).start();
            new Thread(helloRunnable).start();
        }
        System.out.println("Hello from Main Thread");
    }

    private static class HelloWorld extends Thread {
        @Override
        public void run() {
            System.out.println("Hello from " + getName());
        }
    }

    private static class HelloRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello from " + Thread.currentThread().getName()); // Только чтение из памяти
        }
    }
}
