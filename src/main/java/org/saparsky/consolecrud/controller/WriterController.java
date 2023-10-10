package org.saparsky.consolecrud.controller;

import org.saparsky.consolecrud.enums.WriterStatus;
import org.saparsky.consolecrud.model.Post;
import org.saparsky.consolecrud.model.Writer;
import org.saparsky.consolecrud.repository.gson.GsonWriterRepositoryImpl;

import java.util.List;

public class WriterController {
    private final GsonWriterRepositoryImpl writerRepository;

    public WriterController(GsonWriterRepositoryImpl writerRepository) {
        this.writerRepository = writerRepository;
    }


    public void update(Long id, String firstName, String lastName, WriterStatus writerStatus) {
        Writer writer = Writer.builder()
                        .id(id)
                        .firstName(firstName)
                        .lastName(lastName)
                        .writerStatus(writerStatus)
                        .build();
        writerRepository.update(writer);
    }

    public void delete(Long id) {
        writerRepository.deleteById(id);
    }

    public List<Writer> getAll() {
        return writerRepository.getAll();
    }

    public Writer create(String name, String lastName, List<Post> posts, WriterStatus writerStatus) {
        Writer writer = Writer.builder()
                .firstName(name)
                .lastName(lastName)
                .posts(posts)
                .writerStatus(writerStatus)
                .build();
        return writerRepository.save(writer);
    }

    public Writer getWriterWithId(Long id) {
        return writerRepository.getById(id);
    }


    public Writer addPostToWriter(Long idWriter, Post newPost) {
        Writer writer = writerRepository.getById(idWriter);
        writer.addPost(newPost);
        return writerRepository.update(writer);
    }
}
