package Lamda_Functional_Interface.Stream;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;

//=================================================================================
public class Uorberton {
    /*
    int count = 0;

    ArrayList<String> col = new ArrayList<>();
        col.add("azaza");
        col.add("ozozo");
        col.add("azaza");
        col.add("ozozo");
        col.add("azaza");
        col.add("ozozo");
    ////////////////////////////////////////////////////////////////////////////////////
    Iterator<String> iterator = col.iterator();
        while (iterator.hasNext()) {
        String tempStr = iterator.next();
        if (Objects.equals(tempStr, "azaza")) {
            count++;
        }
    }
        System.out.println(count);
    count = 0;
////////////////////////////////////////////////////////////////////////////////////
        for (String sear : col) {
        if (Objects.equals(sear, "azaza")) {
            count++;
        }
    }
        System.out.println(count);
    ////////////////////////////////////////////////////////////////////////////////////
    long countTOP = col.stream().filter(t1 -> t1.equals("azaza")).count();
        System.out.println(countTOP);
*/
    @SuppressWarnings("Convert2MethodRef")
    public static void main(String[] args) {
        // В своем функциональном интерфейсе: interface Function<T, R>, методом: R apply(T t),
        // функция map: <R> Stream<R> map(Function<? super T, ? extends R> mapper),
        // требует, чтобы ее параметр принимал <T>, а возвращал <R>. (apply)

        System.out.println(Stream.of("ololo", "azaza")
                .map(String -> String.toUpperCase())        // (String -> String.toUpperCase) == String::toUpperCase
                .collect(Collectors.toList()));

        // Пример использования функционального интерфейса через анонимный класс и ссылку на метод.
        // Function<String, String> all = new Function<>() {
        //    @Override
        //        public String apply(String o) {
        //            return o.toUpperCase();
        //        }
        // };
        // System.out.println(all.apply("eeeee"));
        // Или Function<String, String> jjj = String::toUpperCase;
        // System.out.println(jjj.apply("eeeee"));

//=================================================================================
        // В своем функциональном интерфейсе: public interface BinaryOperator<T> extends BiFunction<T,T,T>,
        // методом: R apply(T t, U u) от public interface BiFunction<T, U, R>,
        // функция reduce: T reduce(T identity, BinaryOperator<T> accumulator),
        // требует, чтобы ее параметр принимал <T, T>, а возвращал <T>. (apply),
        // это по Уорбертону, по IDE: принимал <T, U>, возвращал: <R>

        System.out.println(Stream.of(1.5, 2.5, 3.3)
                .reduce(0.0, (acc, temp) -> acc + temp));

        // Пример использования функционального интерфейса через анонимный класс и ссылку на метод.
        // BinaryOperator al2l = new BinaryOperator() {
        //     @Override
        //     public Object apply(Object o, Object o2) {
        //         return null;
        //     }
        // };
        // BinaryOperator<Integer> jfps = new BinaryOperator<Integer>() {
        //     @Override
        //     public Integer apply(Integer integer, Integer integer2) {
        //         return null;
        //     }
        // };
        // BinaryOperator<Integer> jfps = new BinaryOperator<Integer>() {
        //     @Override
        //     public Integer apply(Integer integer, Integer integer2) {
        //         return null;
        //     }
        // };
//=================================================================================
        Function<Integer, Double> lll = (x) -> x / 25.0;  // Function<T, R>; R apply(T t);
        System.out.println(lll.apply(50));

        System.out.println(MapUsingReduce.map7(Stream.of(12), (Integer) -> Integer.toString()));
    }
}
// Выбираем реализацию reduce(U identity, BiFunction<U, ? super Object, U> accumulator, BinaryOperator<U> combiner) возвращающую U.
// И BiFunction<T,U,R> в ней, вместе с R apply(T t, U u) превращается в BiFunction<U,?,U> с U apply(U t, ? u);
// На выходе метода функционального интерфейса Function<I,O> должен быть тип O (Integer.toString()), а на входе тип I (Integer).
// Далее функциональный интерфейс BiFunction<U,?,U> с методом U apply(U t, ? u), внутри reduce, принимает List<O> в качестве U, и I в качестве ?.
// Вернуть его метод должен соответственно U, т.е. List<O> этой строкой: return newAcc.
// Внутри возвращаемого List<O> будет mapper.apply(x), где входной x имеет тип I, а выходной тип станет List<O>, т.е. O.
// Так как метод функционального интерейса Function<I, O> все тот же O apply(I t), где mapper.apply == Integer.toSting().
// На выходе из reduce получится List<O> как того и требует map7.

class MapUsingReduce {
    static <I, O> List<O> map7(Stream<I> stream, Function<I, O> mapper) {
        return stream.reduce(new ArrayList<O>(), (List<O> acc, I x) -> {
            List<O> newAcc = new ArrayList<>(acc);
            newAcc.add(mapper.apply(x));    // Вызов R apply(T t) из Function<T,R> из mapper.
            return newAcc;
        }, (List<O> left, List<O> right) -> {
            List<O> newLeft = new ArrayList<>(left);
            newLeft.addAll(right);
            return newLeft;
        });
    }
}







