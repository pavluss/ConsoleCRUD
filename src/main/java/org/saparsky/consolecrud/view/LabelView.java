package org.saparsky.consolecrud.view;

import org.saparsky.consolecrud.controller.LabelController;
import org.saparsky.consolecrud.enums.LabelStatus;
import org.saparsky.consolecrud.model.Label;

import java.util.List;
import java.util.Scanner;

public class LabelView {
    private final LabelController labelController;
    private final Scanner scanner;

    public LabelView(LabelController labelController, Scanner scanner) {
        this.labelController = labelController;
        this.scanner = scanner;
    }

    public void show() {
        boolean isContinue = true;
        while (isContinue) {
            String menuLabel = """
                    Выберете действие над метками:
                    1. Показать все
                    2. Показать c ID
                    3. Создать
                    4. Изменить
                    5. Удалить
                    6. Выход""";
            System.out.println(menuLabel);
            String action = scanner.next();
            switch (action) {
                case "1" -> getAllLabels();
                case "2" -> getLabelWithId();
                case "3" -> create();
                case "4" -> update();
                case "5" -> delete();
                case "6" -> isContinue = false;
                default -> System.out.println("Неверный ввод");
            }
        }
    }

    private void getLabelWithId() {
        System.out.println("Введите ID метки для вывода:");
        Long id = scanner.nextLong();
        Label label = labelController.getLabelWithId(id);
        System.out.printf("Метка с ID %s: %s%n", id, label);
    }

    private void delete() {
        System.out.println("Введите ID метки для удаления:");
        Long id = scanner.nextLong();
        labelController.delete(id);
    }

    private void update() {
        System.out.println("Введите ID метки для обновления:");
        Long id = scanner.nextLong();

        System.out.println("Введите новое название метки:");
        String name = scanner.next();
        LabelStatus labelStatus = LabelStatus.ACTIVE;

        Label updateLabel = labelController.update(id, name, labelStatus);
        System.out.println("Обновлена метка с ID: " + updateLabel.getId());
    }

    private void create() {
        System.out.println("Введите имя метки:");
        String name = scanner.next();
        LabelStatus labelStatus = LabelStatus.ACTIVE;
        Label label = labelController.create(name, labelStatus);
        System.out.println("Добавлена новая метка с ID: " + label.getId());
    }

    private void getAllLabels() {
        List<Label> labelList = labelController.getAllLabels();
        for (Label label: labelList) {
            System.out.println(label);
        }
    }
}
