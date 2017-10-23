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
*/

public class Synchronized {
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(100_000);
        System.out.println("Начальный баланс: " + account.getBalance());
        Thread depositThread = new DepositThread(account);
        Thread withdrawThread = new WithDrawThread(account);

        depositThread.start();
        withdrawThread.start();

        depositThread.join();
        withdrawThread.join();

        System.out.println("End balance: " + account.getBalance());
    }
}

class WithDrawThread extends Thread{
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
        for (int i = 0; i < 50000; i++) {
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
    }

     synchronized void withdraw(long amount) {
        checkAmountNonNegative(amount);
        if (balance < amount) {
            throw new IllegalArgumentException("Денег нет, но вы держитесь");
        }
        balance -= amount;
    }

    private void checkAmountNonNegative(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("negative amount");
        }
    }
}
