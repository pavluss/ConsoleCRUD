package org.saparsky.consolecrud.view;

import org.saparsky.consolecrud.controller.PostController;
import org.saparsky.consolecrud.enums.LabelStatus;
import org.saparsky.consolecrud.enums.PostStatus;
import org.saparsky.consolecrud.model.Label;
import org.saparsky.consolecrud.model.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private final PostController postController;
    private final Scanner scanner;

    public PostView(PostController postController, Scanner scanner) {
        this.postController = postController;
        this.scanner = scanner;
    }

    public void show() {
        boolean isContinue = true;
        while (isContinue) {
            String menuPost = """
                    Выберете действие над постами:
                    1. Показать все
                    2. Показать по ID
                    3. Создать
                    4. Изменить
                    5. Удалить
                    6. Добавить метку к посту
                    7. Выход""";
            System.out.println(menuPost);
            String action = scanner.next();
            switch (action) {
                case "1" -> getAllPosts();
                case "2" -> getPostWithId();
                case "3" -> create();
                case "4" -> update();
                case "5" -> delete();
                case "6" -> addLabelToPost();
                case "7" -> isContinue = false;
                default -> System.out.println("Неверный ввод");
            }
        }

    }

    private void addLabelToPost() {
        System.out.println("Введите ID поста:");
        Long idPost = scanner.nextLong();

        System.out.println("Введите имя метки:");
        String name = scanner.next();

        LabelStatus labelStatus = LabelStatus.ACTIVE;
        Label label = new Label(name, labelStatus);
        Post post = postController.addLabelToPost(idPost, label);
        System.out.printf("Метка добавлена к посту c ID: %s%n", post.getId());
    }

    private void delete() {
        System.out.println("Введите ID поста для удаления:");
        Long id = scanner.nextLong();
        postController.delete(id);
    }

    private void update() {
        System.out.println("Введите ID поста для обновления:");
        Long id = scanner.nextLong();

        System.out.println("Введите новое содержимое поста:");
        String content = scanner.next();

        Date updated = new Date();
        PostStatus postStatus = PostStatus.ACTIVE;

        postController.update(id, content, updated, postStatus);
    }

    private void create() {
        System.out.println("Введите содержимое поста:");
        String content = scanner.next();

        Date created = new Date();
        Date updated = new Date();

        List<Label> labels = new ArrayList<>();

        PostStatus postStatus = PostStatus.ACTIVE;

        Post newPost = postController.create(content, created, updated, labels, postStatus);
        System.out.printf("Создан пост с ID: %s%n", newPost.getId());

    }

    private void getPostWithId() {
        System.out.println("Введите ID поста:");
        Long id = scanner.nextLong();
        Post post = postController.getPostWithId(id);
        System.out.println(post);
    }

    private void getAllPosts() {
        List<Post> postList = postController.getAll();
        for (Post post: postList) {
            System.out.println(post);
        }
    }
}
