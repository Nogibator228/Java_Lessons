package Interface_Package;


interface CallBackStart {
    static int StaticInterfaceMethod(int n) {
        return n;
    }

    void CallbackSet(int param);

    default String getString() {
        return "Объект типа String по умолчанию.";
    }
}

class Client implements CallBackStart {
    @Override
    public void CallbackSet(int p) {
        System.out.println("Метод CallbackSet(), вызванный со значением " + p);
    }

    void nonIfaceMeth() {
        System.out.println("Другой член в классе реализующим интерфейс");
    }
}

class AnotherClient implements CallBackStart {
    @Override
    public void CallbackSet(int p) {
        System.out.println("p в квадрате равно " + (p * p));
    }
}

class SomeClass {
    interface Callback {
        void CallingBack();
    }

    private Callback callback;

    void registerCallBack(Callback callback) {
        this.callback = callback;
    }

    void doSomething() {
        System.out.println("Do Something");
        callback.CallingBack();
    }
}

class MyClass implements SomeClass.Callback {
    @Override
    public void CallingBack() {
        System.out.println("Тело метода обратного вызова");
    }
}

public class Shildt_Chapter_9 {

    public static void main(String[] args) throws InterruptedException {
/////////////////////////Пример коллбэка с сайта fandroid.info №1///////////////////////////////
        SomeClass someclass = new SomeClass();
        MyClass myclass = new MyClass();

        someclass.registerCallBack(myclass);
        someclass.doSomething();

        CallBackStart c = new Client();
        AnotherClient ob = new AnotherClient();

        c.CallbackSet(42);
        c = ob;
        c.CallbackSet(42);
        System.out.println(c.getString());
        // c.nonIfaceMeth(); Недоступно

        System.out.println("Статичный метод из интерфейса не наслеуется и не реализуется: " + CallBackStart.StaticInterfaceMethod(10));
/////////////////////////Защита доступа/////////////////////////////////
        System.out.println("                       private    public    protected     default");
        System.out.println("один класс                +           +          +             + ");
        System.out.println("");
        System.out.println("класс из того             -           +          +             + ");
        System.out.println("же пакета,");
        System.out.println("не подкласс");
        System.out.println("");
        System.out.println("класс - подкласс          -           +          +             - ");
        System.out.println("производный от");
        System.out.println("класса другого");
        System.out.println("пакета");
        System.out.println("");
        System.out.println("подкласс, производный     -           +          +             + ");
        System.out.println("от класса, этого же");
        System.out.println("пакета");
        System.out.println("");
        System.out.println("класс, из др-го пакета    -           +          -             - ");
        System.out.println("не являющ-ся подклассом");
        System.out.println("производным от класса");
        System.out.println("из данного пакета");
        System.out.println("");

    }
}