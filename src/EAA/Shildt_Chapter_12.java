package EAA;

import java.lang.annotation.*;
import java.lang.reflect.Method;// Перечисления, автоупаковка, аннотации, Глава 12, стр. 317 - 354.
// Одна аннотация не может наследовать другую.
// Все методы объявленные в аннотациях должны быть без параметров
// Они должны возвращать: примитивный тип, объект класса String или Class, перечисляемый тип, тип другой аннотации, массив одного из предыдущих типов.
// Аннотации не могут быть обобщенными.
// При объявлении методов в аннотациях нельзя указывать оператор throws.
import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME) // Аннотация-повтор
@Target({ElementType.METHOD})
@Repeatable(MyRepeatAnnos.class)
@interface RepeatAnno { String description() default "Repeat"; }

@Retention(RetentionPolicy.RUNTIME) // Аннотация-контейнер
@Target({ElementType.METHOD})
@interface MyRepeatAnnos { RepeatAnno[] value(); }
///////////////////////////////////////////////////////////

@Retention(RetentionPolicy.RUNTIME) // Аннотация-маркер
@Target({ElementType.TYPE_USE})
@interface TypeAnno { String description() default "rush"; }

@Retention(RetentionPolicy.RUNTIME) // Аннотация-маркер
@interface MyMarker { }

@Retention(RetentionPolicy.RUNTIME) // Одночленная аннотация
@Target({ElementType.FIELD, ElementType.METHOD})
@interface MySingle { int value(); } // Должно быть имя value()

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnno {
    int val();
    String str() default "KanalyA";
}

@MyMarker
public class Shildt_Chapter_12 {
    /////////////////////////Перечисления///////////////////////////
    enum Apple {
        Jonathan(2), GoldenDel(30), RedDel(20), Winesap(5), Cortland(9); // Константы перечисляемого типа
        private int price;
        Apple (int p) {price = p;} // Возможны конструкторы по умолчанию, а также перегруженные.
        int getPrice() {return price;}
    } //Открытые, статические и конечные члены класса Apple, самотипизированные константы.
    // Получить экземпляры класса enum нельзя.

    //////////////////////Аннотации (метаданные)///////////////////////
    @MyAnno(val = 103)
    @RepeatAnno(description = "lil")
    @RepeatAnno(description = "lal")
    public static void MyMeth (int k, String lol) {
        Shildt_Chapter_12 obj = new Shildt_Chapter_12();
        try {
            Class<?> c = obj.getClass();
            if (c.isAnnotationPresent(MyMarker.class)) {System.out.println("Есть маркер!");}
            @TypeAnno(description = "getMeth_haha") Method m = c.getMethod("MyMeth", int.class, String.class);
            MyAnno anno = m.getAnnotation(MyAnno.class);
            Annotation anno2 = m.getAnnotation(MyRepeatAnnos.class);
            Annotation[] repeatAnnos = m.getAnnotationsByType(RepeatAnno.class);
            for(Annotation a:repeatAnnos) {
                System.out.println("--"+a+"--");
            }
            System.out.println(anno2);
            // MyAnno.class - Данное выражение, вычисляется как объект Class, относящийся к типу MyAnno, т.е. к искомой аннотации.
            // Оно называется литералом класса, литерал можно использовать всякий раз, когда требуется объект Class известного класса.
            // К примеру: Class<?> c = Main.class; Литерал класса можно получить для классов, интерфейсов, примитивных типов и массивов.
            System.out.println(anno.str() + " " +anno.val());

            Annotation annos[] = m.getAnnotations();
            for (Annotation a: annos) {
                System.out.println("==" + a);
            }
        } catch (NoSuchMethodException exc) {
            System.out.println("Метод не найден");
        }
    }
    @MySingle(88)
    public static void main(String[] args) throws NoSuchMethodException {
        MyMeth(22, "lalka");

        Apple ap;
        ap = Apple.Jonathan;
        System.out.println(ap);

        Apple allapples[] = Apple.values();
        Apple one = Apple.valueOf("Winesap");

        System.out.println(allapples[2]);
        System.out.println(one);

        System.out.println("Цена яблока " + allapples[2] + ": " + allapples[2].getPrice());

        // Перечисления допускают предоставление конструкторов, добавление переменных, экземпляров и методов и даже реализацию интерфейсов.
        // Каждая константа перечисляемого типа это объект класса своего перечисления. При создании константы, вызывается конструктор, если он задан.
        // Нет возможности наследоваться от другого класса, и перечисление не может стать суперклассом (не может быть расширено).
        // Перечисления наследуются от класса Enum.
        System.out.println("Позиция в списке константы " + allapples[2] + ": " + allapples[2].ordinal());
        System.out.println(allapples[3].compareTo(allapples[2]));
        System.out.println(allapples[3].equals(allapples[3]));

//////////////////////Оболочки типов///////////////////////
        Integer iOb = new Integer(100); // Упаковка
        int i = iOb.intValue(); // Распаковка

        Integer iOb2 = 1000; // Автоупаковка.
        ++iOb2; // Автоупаковка в выражениях.
        int i2 = iOb2.byteValue();
        System.out.println(i2);
    }
}
