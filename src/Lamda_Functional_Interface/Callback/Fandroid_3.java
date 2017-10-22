package Lamda_Functional_Interface.Callback;

import java.util.Arrays;
import java.util.List;

interface ICustomOperation {
    String evaluate(String input);
}

class Template {
    private ICustomOperation callback;

    Template(ICustomOperation callback) {
        this.callback = callback;
    }

    void setCallback(ICustomOperation callback) {
        this.callback = callback;
    }

    void processData(List<String> lines) {
        for (String line : lines) {
            System.out.println("Input: " + line);
            line = "[" + line + "]";
            System.out.println("Standart operation 1 result: " + line);
            line = line.replaceAll(" ", "_");
            System.out.println("Standart operation 2 result: " + line);
            line = callback.evaluate(line); // Контроль над обработкой - пользователю.
            System.out.println("Custom operation result: " + line);
            line = line.toLowerCase();
            System.out.println("Standart operation 3 result: " + line);
            System.out.println();
        }
    }
}

class CustomOperaion implements ICustomOperation {
    @Override
    public String evaluate(String input) {
        return "CUSTOM: " + input;
    }
}

class AnotherCustomOperaion implements ICustomOperation {
    @Override
    public String evaluate(String input) {
        return "ANOTHER CUSTOM: " + input;
    }
}

public class Fandroid_3 {
    public static void main(String[] args) {
        Template template = new Template(new CustomOperaion());
        List<String> data = Arrays.asList("first line", "SeCoND LiNE");
        template.processData(data);
        template.setCallback(new AnotherCustomOperaion());
        template.processData(data);
    }
}