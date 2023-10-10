package org.saparsky.consolecrud.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.saparsky.consolecrud.enums.WriterStatus;
import org.saparsky.consolecrud.exceptions.NotFoundException;
import org.saparsky.consolecrud.model.Post;
import org.saparsky.consolecrud.model.Writer;
import org.saparsky.consolecrud.repository.WriterRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class GsonWriterRepositoryImpl implements WriterRepository {

    private final String FILE_PATH = "src/main/resources/writers.json";
    private final Gson gson = new Gson();

    private final GsonPostRepositoryImpl postRepository = new GsonPostRepositoryImpl();

    private void saveWritersToJson(List<Writer> writerList) {
        try (FileWriter writersJson = new FileWriter(FILE_PATH)) {
            gson.toJson(writerList, writersJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Writer> getWritersFromJson() {
        try (FileReader writersJson = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<List<Writer>>(){}.getType();
            return gson.fromJson(writersJson, type);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private long getNextId(List<Writer> currentWriters) {
        return currentWriters.stream()
                .map(Writer::getId)
                .max(Long::compare)
                .orElse(0L) + 1;
    }

    @Override
    public Writer getById(Long id) {
        return getWritersFromJson().stream()
                .filter(writer -> writer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Writer with %s not found".formatted(id)));
    }

    @Override
    public List<Writer> getAll() {
        return getWritersFromJson();
    }

    @Override
    public Writer save(Writer writer) {
        List<Writer> currentWriters = getWritersFromJson();
        long nextId = getNextId(currentWriters);
        writer.setId(nextId);
        currentWriters.add(writer);
        saveWritersToJson(currentWriters);
        return writer;
    }

    @Override
    public Writer update(Writer updateWriter) {
        List<Writer> currentWriters = getWritersFromJson();
        List<Writer> updatedWriters = currentWriters.stream()
                .peek(existingWriter -> {
                    if (existingWriter.getId().equals(updateWriter.getId())) {
                        existingWriter.setFirstName(updateWriter.getFirstName());
                        existingWriter.setLastName(updateWriter.getLastName());
                        existingWriter.setWriterStatus(updateWriter.getWriterStatus());

                        if (updateWriter.getPosts() != null) {
                            for (Post post: updateWriter.getPosts()) {
                                if (!existingWriter.getPosts().contains(post)) {
                                    postRepository.save(post);
                                }
                            }
                            existingWriter.setPosts(updateWriter.getPosts());
                        }
                    }
                }).toList();
        saveWritersToJson(updatedWriters);
        return updateWriter;
    }

    @Override
    public void deleteById(Long id) {
        List<Writer> currentWriters = getWritersFromJson();
        List<Writer> updatedWriters = currentWriters.stream()
                .peek(writer -> {
                    if (writer.getId().equals(id)) {
                        writer.setWriterStatus(WriterStatus.DELETED);
                    }
                }).toList();
        saveWritersToJson(updatedWriters);
    }
}
