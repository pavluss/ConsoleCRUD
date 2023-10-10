package org.saparsky.consolecrud.view;

import org.saparsky.consolecrud.controller.WriterController;
import org.saparsky.consolecrud.enums.PostStatus;
import org.saparsky.consolecrud.enums.WriterStatus;
import org.saparsky.consolecrud.model.Label;
import org.saparsky.consolecrud.model.Post;
import org.saparsky.consolecrud.model.Writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class WriterView {
    private final WriterController writerController;
    private final Scanner scanner;

    public WriterView(WriterController writerController, Scanner scanner) {
        this.writerController = writerController;
        this.scanner = scanner;
    }

    public void show() {
        boolean isContinue = true;
        while (isContinue) {
            String menuWriter = """
                    Выберете действие над авторами:
                    1. Показать всех
                    2. Показать по ID
                    3. Создать
                    4. Изменить
                    5. Удалить
                    6. Добавить пост к автору
                    7. Выход""";
            System.out.println(menuWriter);
            String action = scanner.next();
            switch (action) {
                case "1" -> getAllWriters();
                case "2" -> getWriterWithId();
                case "3" -> create();
                case "4" -> update();
                case "5" -> delete();
                case "6" -> addPostToWriter();
                case "7" -> isContinue = false;
                default -> System.out.println("Неверный ввод");
            }
        }
    }

    private void addPostToWriter() {
        System.out.println("Введите ID автора:");
        Long idWriter = scanner.nextLong();

        System.out.println("Введите текс поста:");
        String content = scanner.next();
        Date created = new Date();
        Date update = new Date();

        List<Label> labels = new ArrayList<>();
        PostStatus postStatus = PostStatus.ACTIVE;

        Post newPost = Post.builder()
                        .content(content)
                        .created(created)
                        .updated(update)
                        .labels(labels)
                        .postStatus(postStatus)
                        .build();


        Writer writer = writerController.addPostToWriter(idWriter, newPost);
        System.out.printf("Статья добавлена к автору c ID: %s%n", writer.getId());

    }

    private void update() {
        System.out.println("Введите ID автора для обновления:");
        Long id = scanner.nextLong();

        System.out.println("Введите новое имя:");
        String firstName = scanner.next();

        System.out.println("Введите новую фамилию:");
        String lastName = scanner.next();

        WriterStatus writerStatus = WriterStatus.ACTIVE;

        writerController.update(id, firstName, lastName, writerStatus);
    }

    private void delete() {
        System.out.println("Введите ID автора для удаления:");
        Long id = scanner.nextLong();
        writerController.delete(id);
    }

    private void getWriterWithId() {
        System.out.println("Введите ID автора:");
        Long id = scanner.nextLong();
        Writer writer = writerController.getWriterWithId(id);
        System.out.printf("Автор с ID %s: %s%n", id, writer);
    }

    private void create() {
        System.out.println("Введите имя автора:");
        String name = scanner.next();
        System.out.println("Введите фамилию автора:");
        String lastName = scanner.next();
        List<Post> posts = new ArrayList<>();
        WriterStatus writerStatus = WriterStatus.ACTIVE;
        Writer writer = writerController.create(name, lastName, posts, writerStatus);
        System.out.println("Добавлен новый автор с ID: " + writer.getId());
    }

    private void getAllWriters() {
        List<Writer> writers = writerController.getAll();
        for (Writer writer: writers) {
            System.out.println(writer);
        }
    }


}
