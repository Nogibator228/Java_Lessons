package IO;

import java.io.*;
import static java.lang.Math.sqrt; // статический импорт

////////////////////Вызовы перегруженных конструкторов по ссылке this()///////////////////////
class A{ // ссылка this или super, должна быть единственной и первой в списке операторов
    // плюс, нельзя использовать переменные экземпляра конструктора который вызывается
    int i,j;
    // transient int oo;
    // volatile int rr;  Оперативное обновление главной копии переменной через измененные копии в потоках доступа
    A (int k, int y) {
        i = k; j = y;
    }
    A (int k) {
        this (k, k);
    }
    A () {
        this(0);
    }
}
class B {
    int i,j;
}
class C extends A {
    int k;
}
class D extends A {
    int k;
}

public class Shildt_Chapter_13 {
    static private void pipec(FileInputStream i) throws IOException { // Рекурсивная версия считывания строки из файла
        int n = i.read();
        if (n != -1) {
            System.out.print((char)n);
            pipec(i);
        }
    }
    public static void main(String[] args) throws IOException {
////////////////////Правила действия оператора instanceof///////////////////////
        A a = new A();
        B b = new B();
        C c = new C();
        D d = new D();
        if (a instanceof A) {System.out.println("а относится к А или может быть приведен к А");}
        if (c instanceof A) {System.out.println("c относится к А или может быть приведен к А");}
        if (!(a instanceof C)) {System.out.println("a НЕ относится к C и НЕ может быть приведен к C");}
        A ob;
        ob = d;
        if (ob instanceof D) {System.out.println("ob относится к D или может быть приведен к D");}
        ob = c;
        if (!(ob instanceof D)) {System.out.println("ob НЕ относится к D или НЕ может быть приведен к D");}
        if (ob instanceof A) {System.out.println("ob относится к A или может быть приведен к A");}
        if (ob instanceof Object) {System.out.println("ob относится к Object или может быть приведен к Object");}
        // ob = b; Недопустимо!
        assert (sqrt(4) == 2) : "Сработавшее утверждение";
////////////////////Чтение и запись в файлы///////////////////////
        try (FileInputStream fin = new FileInputStream("111.txt"); FileOutputStream Wr = new FileOutputStream("222.txt")) {
            //pipec(fin);111
            for (int i = fin.read(); i != -1; i = fin.read()) System.out.print((char) i);
            Wr.write('w');
        } catch (IOException e) {
            System.out.println("File not Found or I/O Exception");
        }
        finally {
            System.out.println(" Выполняется в любом случае, даже если исключение в catch словилось, не войдет сюда код - только если помрет JVM");
        }
////////////////////Чтение из консоли///////////////////////
        String[] str2 = new String[100];
        BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
        for (int i=0; i < 100; i++) {
            str2[i] = br2.readLine();
            if (str2[i].equals("стоп")) break;
        }
    }
}