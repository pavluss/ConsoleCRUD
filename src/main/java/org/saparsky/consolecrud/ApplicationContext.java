package org.saparsky.consolecrud;

import org.saparsky.consolecrud.controller.LabelController;
import org.saparsky.consolecrud.controller.PostController;
import org.saparsky.consolecrud.controller.WriterController;
import org.saparsky.consolecrud.repository.gson.GsonLabelRepositoryImpl;
import org.saparsky.consolecrud.repository.gson.GsonPostRepositoryImpl;
import org.saparsky.consolecrud.repository.gson.GsonWriterRepositoryImpl;
import org.saparsky.consolecrud.view.LabelView;
import org.saparsky.consolecrud.view.PostView;
import org.saparsky.consolecrud.view.WriterView;

import java.util.Scanner;

public class ApplicationContext {


    private final Scanner scanner = new Scanner(System.in);

    private final GsonWriterRepositoryImpl writerRepository = new GsonWriterRepositoryImpl();
    private final GsonPostRepositoryImpl postRepository = new GsonPostRepositoryImpl();
    private final GsonLabelRepositoryImpl labelRepository = new GsonLabelRepositoryImpl();

    private final WriterController writerController = new WriterController(writerRepository);
    private final PostController postController = new PostController(postRepository);
    private final LabelController labelController = new LabelController(labelRepository);

    private final WriterView writerView = new WriterView(writerController, scanner);
    private final PostView postView = new PostView(postController, scanner);
    private final LabelView labelView = new LabelView(labelController, scanner);


    public void run() {
        boolean isContinue = true;
        while (isContinue) {
            String menuMessage = """
                    Выберете действие:
                    1. Авторы
                    2. Посты
                    3. Метки
                    4. Выход""";
            System.out.println(menuMessage);
            String action = scanner.next();
            switch (action) {
                case "1" -> writerView.show();
                case "2" -> postView.show();
                case "3" -> labelView.show();
                case "4" -> isContinue = false;
                default -> System.out.println("Неверный ввод");
            }
        }
    }
}
