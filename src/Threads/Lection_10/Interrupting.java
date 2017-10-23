package Threads.Lection_10;

/*
Метод interrupted объекта Thread для завершения потока.
1) Если поток, находился в одном из методов ожидания (sleep, join, wait), будет выбрашено проверяемое исключение.
2) Если поток совершал вычислительные действия, то необходимо самостоятельно проверять запрос на остановку специальным флагом,
флаг проверяется методами interrupted() или isIntereupted() и завершить поток вручную.
Метод interrupted() возвращает управление сразу. И это не значит, что тот поток, которому сказано остановиться - остановился.
Дождаться, чтобы этот поток корректно остановился - методом join(). У join() есть перегруженный вариант с time_out'ом.
*/

public class Interrupting {
    public static void main(String[] args) throws Exception {
        Thread worker = new WorkerThread();
        // worker.setDaemon(true);
        Thread sleeper = new SleeperThread();
        // sleeper.setDaemon(true);

        System.out.println("Starting Threads");
        worker.start();
        sleeper.start();

        Thread.sleep(300L);

        System.out.println("Interrupting Threads");
        worker.interrupt();
        sleeper.interrupt();

        System.out.println("Joining Threads");
        worker.join();
        sleeper.join();

        System.out.println("All done");
    }

    private static class WorkerThread extends Thread {
        @Override
        public void run() {
            long sum = 0;
            for (int i = 0; i < 1_000_000_000; ++i) {
                sum += i;
                if (i % 100 == 0 && isInterrupted()) {
                    System.out.println("Цикл обработки остановился на i = " + i);
                    break;
                }
            }
        }
    }

    private static class SleeperThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                System.out.println("Sleep was interrupted!!!");
            }
        }
    }
}