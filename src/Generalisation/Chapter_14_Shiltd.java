package Generalisation;

/*=======Введение в обощения=======*/
class Gen<T> {  // Обощения действуют только со ссылочными типами, не примитивными. T - параметр типа, заполнитель.
    private T ob;

    Gen(T o) {
        ob = o;
    }

    T getOb() {
        return ob;
    }

    void showType(char c) {
        System.out.println("Тип " + c + ": " + ob.getClass().getName());
    }
}

/*=======Обобщенный класс с двумя параметрами типа=======*/
class TwoGen<T, V> { // Обобщенный класс с двумя параметрами типа.
    private T ob1;
    private V ob2;

    TwoGen(T o1, V o2) {
        ob1 = o1;
        ob2 = o2;
    }

    void showTypes() {
        System.out.println("Тип 3: " + ob1.getClass().getName());
        System.out.println("Тип 4: " + ob2.getClass().getName());
    }

    T getOb1() {
        return ob1;
    }

    V getOb2() {
        return ob2;
    }
}

/*=======Ограниченные типы=======*/
/* Ограничения могут включать в себя также и типы интерфейсов. Тип класса должен быть задан первым.
*  Когда наложено ограничение с интерфейсами, допустимы только аргументы типа, реализующие этот интерфейс.
*  class Gen<T extends MyClass & MyInterface { //..}
*  Ограничение в виде верхней границы, где объявляется суперкласс (включая), от которого должны быть унаследованы все аргументы типов.*/
class Stats<T extends Number> {
    private T[] Nums;

    Stats(T[] o) {
        Nums = o;
    }

    double average() {
        double sum = 0.0;
        for (T Num : Nums) {
            sum += Num.doubleValue(); // Без <T extends Number> будет ошибкой, т.к. doubleValue() определена только в Number
        }
        return (sum / Nums.length);
    }

    // Метасимвольный аргумент совпадает с любым достоверным объектом класса Stats.
    boolean someAvg(Stats<?> ob) {  // В случае использования параметра Stats<T>, требовалось бы совпадение типов вызываемого и сравниваемого объектов.
        return average() == ob.average();
    }
}

/*=======Ограниченные метасимвольные аргументы=======*/
class TwoD {
    int x, y;

    TwoD(int a, int b) {
        x = a;
        y = b;
    }
}

class ThreeD extends TwoD {
    int z;

    ThreeD(int a, int b, int c) {
        super(a, b);
        z = c;
    }
}

class FourD extends ThreeD {
    int t;

    FourD(int a, int b, int c, int d) {
        super(a, b, c);
        t = d;
    }
}

class Coords<T extends TwoD> {
    T[] coords;

    Coords(T[] o) {
        coords = o;
    }
}

/*=======Обобщенные конструкторы=======*/
class GenCons {
    private double val;

    <T extends Number> GenCons(T arg) {
        val = arg.doubleValue();
    }

    void showVal() {
        System.out.println("val = " + val);
    }
}

/*=======Обобщенные интерфейсы=======*/
// Как правило класс реализующий обобщенный интерфейс также должен быть обобщенным,
// но если обобщенный интерфейс имеет конкретный тип - class MyClass implements MinMax<Integer>
// то реализующий класс не обязан быть обобщенным
interface MinMax<T extends Comparable<T>> {
    T min();

    T max();
}

// Выражение class MyClass<T extends Comparable<T>> implements MinMax<T extends Comparable<T>>
// недопустимо , т.к. однажды установленный параметр типа
// просто передается интерфейсу без последующих видоизменений
class MyClass<T extends Comparable<T>> implements MinMax<T> {
    private T[] vals;

    MyClass(T[] o) {
        vals = o;
    }

    public T min() {
        T v = vals[0];
        for (int i = 0; i < vals.length; i++) {
            if (vals[i].compareTo(v) < 0) v = vals[i];
        }
        return v;
    }

    public T max() {
        T v = vals[0];
        for (int i = 1; i < vals.length; i++) {
            if (vals[i].compareTo(v) > 0) v = vals[i];
        }
        return v;
    }
}

/*=======Иерархии обощенных классов, применение обобщенного суперкласса=======*/
// Любые аргументы типа, требующиеся обобщенному суперклассу, должны передаваться всеми подклассами вверх по иерархии.
class GenIE<T> {
    T ob;

    GenIE(T o) {
        ob = o;
    }

    T getOb() {
        return ob;
    }
}

class GenIE2<T, V> extends GenIE<T> {
    V ob2;

    GenIE2(T o, V o2) {
        super(o);
        ob2 = o2;
    }

    V getOb2() {
        return ob2;
    }
}
/*=======Обобщенный подкласс=======*/
// Суперклассом для обощенного подкласса может служить и необощенный класс.


/*=======Сравнение типов в обобщенной иерархии во время выполнения=======*/
// instance определяет является ли объект экземпляром класса, он возвращает true,
// если объект относится к указаноому типу или может быть приведен к этому типу.
class GenC<T> {
    T obj;

    GenC(T o) {
        obj = o;
    }

    T getObj() {
        return obj;
    }
}

class Gen2C<T> extends GenC<T> {
    Gen2C(T o) {
        super(o);
    }
}

/*============================Функция Main()=============================*/
public class Chapter_14_Shiltd {
    public static void main(String[] args) {
///////////////////////////Введение в обобщения///////////////////////////////
        Gen<Integer> iOb = new Gen<>(88);   // Допустимо и: Gen<Integer> iOb = new Gen<Integer>(88), из предыдущих версий;
        Gen<String> strOb = new Gen<>("Строка");
        //iOb = strOb; Ссылки на разные типы объектов, что недопустимо!
        // Ошибка возникает до ввода обобщений, когда параметризированный тип заменен на тип Object;
        int v = iOb.getOb();    // int v = (Integer)iOb.getOb(); Допустимо! Производится при выводе в случае замены T на Object, до ввода обобщений;
        String str = strOb.getOb();

        iOb.showType('1');
        System.out.println("Объект 1: " + v);
        strOb.showType('2');
        System.out.println("Объект 2: " + str);
        System.out.println("1----------------------------");
//////////////////Обобщенные классы с двумя параметрами типа//////////////////////
        TwoGen<Integer, String> tgObj = new TwoGen<>(99, "Обобщения");
        int v2 = tgObj.getOb1();
        String str2 = tgObj.getOb2();

        tgObj.showTypes();
        System.out.println("Объект 3: " + v2);
        System.out.println("Объект 4: " + str2);
        System.out.println("2----------------------------");
/////////////////////////////Ограниченные типы////////////////////////////////////
        Integer[] iNums = {1, 2, 3, 4, 5};
        Stats<Integer> iNumOb = new Stats<>(iNums);
        System.out.println("Среднее: " + iNumOb.average());

        Double dNums[] = {1.1, 2.2, 3.3, 4.4, 5.5};
        Stats<Double> dob = new Stats<>(dNums);
        System.out.println("Среднее: " + dob.average());
        //Stats<String> str = new Stats<>(); // Не скомпилируется, т.к.
///////////////////Применение метасимвольных аргументов///////////////////////////
        if (iNumOb.someAvg(dob)) {
            System.out.println("Средние значения одинаковые");
        } else {
            System.out.println("Средние значения разные");
        }
        System.out.println("3----------------------------");
///////////////////Ограниченные метасимвольные аргументы/////////////////////////
//Ограничение метасимвольного аргумента, в данном случае - чтобы разные объекты из массивов объектов не могли вызвать ненужные функции
//<? super подкласс> - допустимы суперлассы, до данного подкласса, не включая его самого.
        TwoD td[] = {new TwoD(0, 0), new TwoD(18, 4),
                new TwoD(7, 9), new TwoD(-1, -23)};
        FourD fd[] = {new FourD(1, 2, 3, 4), new FourD(6, 8, 14, 8),
                new FourD(29, 9, 4, 9), new FourD(3, -2, -23, 17)};
        Coords<TwoD> tdlocs = new Coords<>(td);
        Coords<FourD> fdlocs = new Coords<>(fd);
        showXY(tdlocs);
        //showXYZ(tdlocs);
        //showAll(tdlocs);
        showXY(fdlocs);
        showXYZ(fdlocs);
        showAll(fdlocs);
        System.out.println("4----------------------------");
////////////////////////Создание обобщенного метода//////////////////////////////
        Integer nums[] = {1, 2, 3, 4, 5};
        if (isIn(5, nums)) System.out.println("Объект 5 обнаружен в массиве nums[]");
        else {
            System.out.println("Не обнаружено в массиве объекта 2");
        }
        System.out.println("5----------------------------");
//////////////////////////Обобщенные конструкторы////////////////////////////////
        GenCons test = new GenCons(100);
        test.showVal();
        System.out.println("6----------------------------");
//////////////////////////Обобщенные интерфейсы//////////////////////////////////
        Integer inums[] = {3, 12, 2, 8, 6};
        MyClass<Integer> iob = new MyClass<>(inums);
        System.out.println("Наибольшее: " + iob.max());
        System.out.println("7----------------------------");
////////////Сравнение типов в обощенной иерархии во время выполнения/////////////
// С помощью метасимвола <?>, оператор instanceof определяет, относится ли объект к какому-либо типу из GenC
// Во время выполнения всякие сведения об обобщенном типе отсутствуют, и оператор instance не может выяснить является ли объект int2Ob
// экземпляром типа Gen2C<Integer> или нет.^^
        GenC<Integer> intOb = new GenC<>(88);
        Gen2C<Integer> int2Ob = new Gen2C<>(99);
        Gen2C<String> str2Ob = new Gen2C<>("LOL");
        if (int2Ob instanceof Gen2C<?>) System.out.println("int2Ob экземпляр Gen2C<?>");
        if (int2Ob instanceof GenC<?>) System.out.println("int2Ob экземпляр GenC<?>");
        if (str2Ob instanceof Gen2C<?>) System.out.println("strOb экземпляр Gen2C<?>");
        if (str2Ob instanceof GenC<?>) System.out.println("strOb экземпляр GenC<?>");
        if (intOb instanceof Gen2C<?>) System.out.println("intOb экземпляр Gen2C<?>");  // не верн1о
        if (intOb instanceof GenC<?>) System.out.println("intOb экземпляр GenC<?>");
        // ^^if (int2Ob instanceof GenC<Integer>) System.out.println("int2Ob экземпляр GenC<Integer>");
// Тип одного экземпляра обощенного класса можно привести к другому только в том случае, ели они совместимы и их аргументы одинаковы.
        // (Gen2C<Integer>)int2Ob; Допустимо
        // (Gen2C<Long>)int2Ob; Недопустимо!
    }

    /*=======Ограниченные метасимвольные аргументы=======*/
    static void showXY(Coords<?> c) {
        System.out.println("Координаты X и Y");
        for (int i = 0; i < c.coords.length; i++) {
            System.out.println(c.coords[i].x + " " + c.coords[i].y);
        }
        System.out.println();
    }

    static void showXYZ(Coords<? extends ThreeD> c) { // Возможность вызвать только объектам от класса <ThreeD> и его наследников <FourD>
        System.out.println("Координаты X Y и Z");
        for (int i = 0; i < c.coords.length; i++) {
            System.out.println(c.coords[i].x + " " + c.coords[i].y + " " + c.coords[i].z);
        }
        System.out.println();
    }

    static void showAll(Coords<? extends FourD> c) {
        System.out.println("Координаты X Y Z и T");
        for (int i = 0; i < c.coords.length; i++) {
            System.out.println(c.coords[i].x + " " + c.coords[i].y + " " + c.coords[i].z + " " + c.coords[i].t);
        }
        System.out.println();
    }

    /*=======Создание обощенного метода=======*/
    static <T extends Comparable<T>, V extends T> boolean isIn(T x, V[] y) {
        for (V aY : y) if (x.equals(aY)) return true;
        return false;
    }
}