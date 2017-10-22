package Lamda_Functional_Interface.Callback;

@FunctionalInterface
interface IPrivateMethodCallback {
    void callPrivateMethod(String caller);
}

// Внешний класс, которому надо вызвать private-метод.
class OuterCaller {
    void callFromOutsidePrivateMethod(IPrivateMethodCallback callback) {
        System.out.println("Outer method call");
        callback.callPrivateMethod("Outer caller ");    // Вызов приватного метода из класса PrivateFieldHolder
    }
}
// Класс держатель приватного метода для передачи.
class PrivateFieldHolder {
    private OuterCaller outerCaller;

    public OuterCaller getOuterCaller() {
        return outerCaller;
    }

    void setOuterCaller(OuterCaller outerCaller) {
        this.outerCaller = outerCaller;
    }

    // Передаем в вызов метода внешнего класса колбэк, который является анонимным классом внутри данного класса,
    // т.к. этот анонимный класс видит private-метод, то мы можем поместить внутрь callPrivateMethod вызов этого private-метода.
    // Таким образом при вызове callPrivateMethod во внешнем классе позволит обратиться к private-методу этого класса.
    void publicMethod() {
        System.out.println("Holder: public method call");
        outerCaller.callFromOutsidePrivateMethod(new IPrivateMethodCallback() {
            public void callPrivateMethod(String caller) {
                privateMethod(caller);
            }
        });
        // Этот анонимный класс может быть заменен следующими строками:
        // outerCaller.callFromOutsidePrivateMethod(caller -> privateMethod(caller));
        // outerCaller.callFromOutsidePrivateMethod(this::privateMethod);
    }

    private void privateMethod(String caller) {
        System.out.println(caller + "private method call");
    }
}

class Fandroid_4 {
    public static void main(String[] args) {
        PrivateFieldHolder pfh = new PrivateFieldHolder();
        OuterCaller oc = new OuterCaller();
        pfh.setOuterCaller(oc);
        pfh.publicMethod();
    }
}