package Lamda_Functional_Interface.Introduction;

interface MyFunc<T> {
    boolean func(T v1, T v2);
}

// Возможны случаи, когда требуется
// указать метод экземпляра, который будет использоваться вместе с любым объектом данного класса, а не только с указанным объектом.
// В таких случаях, создается ссылка на метод экземпляра в форме: имя_класса::имя_метода_экземпляра.
// В этой форме имя класса указывается вместо имени конкретного объекта, не смотря на то что, в ней указывается и метод экземпляра.
// В соответствии с этой формой первый параметр метода из функционального интерфейса совпадает с вызывающим объектом,
// а второй параметр - с параметром, указанным в методе экземпляра.
// Случай - super::имя - обращение к варианту метода из суперкласса.

public class Shildt_Lambda {
    public static void main(String[] args) {
        HighTemp[] weekDayHighs = {new HighTemp(99), new HighTemp(80), new HighTemp(99), new HighTemp(80), new HighTemp(99), new HighTemp(39), new HighTemp(69)};
        System.out.println(InstanceMethWithObjetRefDemo.counter(weekDayHighs, HighTemp::sameTemp, new HighTemp(99)));
        // HighTemp::sameTemp   эквивалентно записи:   (HighTemp x, HighTemp y) -> x.hTemp == y.hTemp
        // При HighTemp aye = new HighTemp(228); и  ,aye::sameTemp,  потребуется функция: boolean sameTemp(HighTemp h2, HighTemp h3) { return h2.hTemp == h3.hTemp; }
    }
}

class HighTemp {
    private int hTemp;

    HighTemp(int ht) {
        hTemp = ht;
    }

    boolean sameTemp(HighTemp h2) {
        return hTemp == h2.hTemp;
    }

    boolean lessThenTemp(HighTemp h2) {
        return hTemp >= h2.hTemp;
    }
}

class InstanceMethWithObjetRefDemo {
    static <T> int counter(T[] vals, MyFunc<T> f, T v) {
        int count = 0;
        for (T val : vals) if (f.func(val, v)) count++;
        return count;
    }
}