# Kонсольное CRUD приложение

Необходимо реализовать консольное CRUD приложение, которое имеет следующие сущности:
```java
Writer (id, firstName, lastName, List<Post> posts)
Post (id, content, created, updated, List<Label> labels)
Label (id, name)
PostStatus (enum ACTIVE, UNDER_REVIEW, DELETED)
```
Каждая сущность имеет поле Status. В момент удаления, мы не удаляем запись из файла, а меняем её статус на DELETED.

В качестве хранилища данных необходимо использовать текстовые файлы:
writers.json, posts.json, labels.json

Пользователь в консоли должен иметь возможность создания, получения, редактирования и удаления данных.

Слои:
* _model_ - POJO клаcсы
* _repository_ - классы, реализующие доступ к текстовым файлам
* _controller_ - обработка запросов от пользователя
* _view_ - все данные, необходимые для работы с консолью

Например: Writer, WriterRepository, WriterController, WriterView и т.д.

Для репозиторного слоя желательно использовать базовый интерфейс:
```java
interface GenericRepository<T,ID> {
    T getById(ID id);
    List<T> getAll();
    T save(T t);
    T update(T t);
    void deleteById(ID id);
}
```

`interface WriterRepository extends GenericRepository<Writer, Integer>`

`class GsonWriterRepositoryImpl implements WriterRepository`

Для работы с json необходимо использовать библиотеку Gson(https://mvnrepository.com/artifact/com.google.code.gson/gson)

Для импорта зависимостей - Maven/Gradle на выбор.