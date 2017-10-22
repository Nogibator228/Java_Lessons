package Lamda_Functional_Interface.Callback;

import java.util.Random;
import java.util.ArrayList;

//////////// Пример коллбэков с сайта fandroid.info (SomeClass & MyClass) №2
public class Fandroid_2 {
    interface CallbackFanDroid {                // Интерфейс, по которому нам будут возвращать данные.
        void callMeBackFanDroid(int status);
    }

    static class Caller implements CallbackFanDroid {
        private final ArrayList<Integer> statuses = new ArrayList<>();

        ArrayList<Integer> getStatuses() {
            return statuses;
        }

        @Override
        public void callMeBackFanDroid(int status) {
            synchronized (statuses) {
                statuses.add(status);
            }
        }
    }

    static class Worker extends Thread {
        private CallbackFanDroid cb;

        Worker(CallbackFanDroid cb) {
            this.cb = cb;
            start();
        }

        int pleaseDoMeAFavor() {
            return new Random().nextInt(100);
        }

        @Override
        public void run() {
            //Выполним работу
            int st = pleaseDoMeAFavor();
            // и вернем данные вызывающему по колбэк интерфейсу
            cb.callMeBackFanDroid(st);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Caller caller = new Caller();

        for (int i = 0; i < 7; i++) {
            Thread t = new Worker(caller);
            t.join();
        }
        // Итого у нас N воркеров отработали и вернули ответ по калбэку в коллекцию
        System.out.println(caller.getStatuses());
    }
}
