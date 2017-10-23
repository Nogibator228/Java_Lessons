package Threads.Lection_10;

/*
1) Взаимное исключение (пока один поток что-то делает, другие не могут ему помешать)
2) Ожидание и уведомление (поток ожидает уведомлений от других потоков)

Синхронизация блоков - по монитору указанного объекта.
Синхронизация методов - по монитору текущего объекта(this).

Если метод статический - то монитором является класс.
Т.е. для всех статических методов - один и тот же монитор.

При нескольких нестатических методах, оба которых синхронайзд, в одном объекте,
блокировка монитором распространяется на все синхронные методы.

Если один метод статический, а другой - нет, то они могут исполняться в разных потоках.
Один будет синхронизироваться по экземпляру, а другой - по классу.
Если монитор захвачен данным объектом, мы вошли в синхронайзд метод,это не помешает этому же потоку исполнить другой синхронайзд метод.

---Ожидание и уведомление---
Вызвав wait() или notify() вне монитора, ничего не произойдет кроме исключения.
wait() блокирует текущий поток и выводит его из монитора, освобождает блокировку монитора
notify() - для этого же объекта - из спячки вернется 1 поток вызвавший ранее wait() на этом же объекте
Рекомендация вызова метода wait нутри цикла проверки while, так как поток может выйти из wait непреднамеренно.

Пару слов о модели памяти.
1) Атомарность.
2) Видимость.

1) Чтение и запись полей всех типов кроме long и double (64 бита), происходит атомарно.
На некоторых платформах разрешается доставать или записывать их в память двумя операциями - по половинке.
Если пишем int, и читаем в другом каком-либо потоке int, то не сможем получить некое мусорное значение: либо то что там было, либо то что там получилось после записи.
Никакого промежуточного варианта получить не выйдет.
Если поле объявлено с модификатором volatile, то атомарно читаются и пишутся даже long или double.

2) Когда один поток видит, что сделал другой с памятью.
Изменения значений полей, сделанные одним потоком, могут быть не видны в другом потоке.
Изменения, сделанные одним потоком, могут быть видны в другом потоке в ином порядке (или не все изменения).
Для гарантии того, чтобы один поток, увидел то, что сделал другой: вводится отношение happens-before, которое говорит:
Если в одном потоке произошло некое событие X, а в другом потоке, после этого произошло событие Z,
то мы гарантировано знаем, все что произошло до X, будет видно здесь после Z.
Основные варианты событий:
- запись volatile поля: событие X, чтение volatile поля: событие Z.
- освобождение (X) и захват монитора (Z).
- запуск Thread: если мы в главном Thread'e запустили Thread.start(), то в запущеном Thread'e метод run() будет видеть корректное состояние памяти.
- если в методе run() что-то поделали с общими перемнными, то гарантируется что мы эти изменения увидим, после того как на этом Thread'e запустим join().
*/

public class Synchronized {
    public static void main(String[] args) throws InterruptedException {
        /*
        Account account = new Account(100_000);
        System.out.println("Начальный баланс: " + account.getBalance());
        Thread depositThread = new DepositThread(account);
        Thread withdrawThread = new WithDrawThread(account);

        depositThread.start();
        withdrawThread.start();

        depositThread.join();
        withdrawThread.join();

        System.out.println("End balance: " + account.getBalance());
        */
        Account account = new Account(0);
        new DepositThread(account).start();
        System.out.println("Calling WaitAndWithdraw()...");
        account.WaitAndWithdraw(50_000_000);
        System.out.println("WaitAndWithdraw() finished");
    }
}

class WithDrawThread extends Thread {
    private final Account account;

    WithDrawThread(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50000; i++) {
            account.withdraw(1);
        }
    }
}

class DepositThread extends Thread {
    private final Account account;

    DepositThread(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50_000_000; i++) {
            account.deposit(1);
        }
    }
}

class Account {
    private long balance;

    public Account() {
        this(0L);
    }

    Account(long balance) {
        this.balance = balance;
    }

    long getBalance() {
        return balance;
    }

    synchronized void deposit(long amount) {
        checkAmountNonNegative(amount);
        balance += amount;
        notifyAll();
    }

    synchronized void withdraw(long amount) {
        checkAmountNonNegative(amount);
        if (balance < amount) {
            throw new IllegalArgumentException("Денег нет, но вы держитесь");
        }
        balance -= amount;
    }

    synchronized void WaitAndWithdraw(long amount) throws InterruptedException{
        checkAmountNonNegative(amount);
        while (balance < amount) {
            wait();
        }
    }

    private void checkAmountNonNegative(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("negative amount");
        }
    }
}
