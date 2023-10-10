package org.saparsky.consolecrud.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.saparsky.consolecrud.enums.LabelStatus;
import org.saparsky.consolecrud.exceptions.NotFoundException;
import org.saparsky.consolecrud.model.Label;
import org.saparsky.consolecrud.repository.LabelRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class GsonLabelRepositoryImpl implements LabelRepository {

    private final String FILE_PATH = "src/main/resources/labels.json";
    private final Gson gson = new Gson();

    private void saveLabelsToJson(List<Label> labelList) {
        try (FileWriter labelsJson = new FileWriter(FILE_PATH)) {
            gson.toJson(labelList, labelsJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Label> getLabelsFromJson() {
        try (FileReader labelsJson = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<List<Label>>(){}.getType();
            return gson.fromJson(labelsJson, type);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private long getNextId(List<Label> labels) {
        return labels.stream()
                .map(Label::getId)
                .max(Long::compare)
                .orElse(0L) + 1;
    }

    @Override
    public Label getById(Long id) {
        return getLabelsFromJson().stream()
                .filter(label -> label.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Label with %s not found".formatted(id)));
    }

    @Override
    public List<Label> getAll() {
        return getLabelsFromJson();
    }


    @Override
    public Label save(Label label) {
        List<Label> currentLabels = getLabelsFromJson();
        long nextId = getNextId(currentLabels);
        label.setId(nextId);
        currentLabels.add(label);
        saveLabelsToJson(currentLabels);
        return label;
    }


    @Override
    public Label update(Label updateLabel) {
        List<Label> currentLabels = getLabelsFromJson();
        List<Label> updatedLabels =  currentLabels.stream()
                .map(existingLabel -> {
                    if (existingLabel.getId().equals(updateLabel.getId())) {
                        return updateLabel;
                    }
                    return existingLabel;
                }).toList();
        saveLabelsToJson(updatedLabels);
        return updateLabel;
    }

    @Override
    public void deleteById(Long id) {
        List<Label> currentLabels = getLabelsFromJson();
        List<Label> updatedLabels = currentLabels.stream()
                .peek(l -> {
                    if (l.getId().equals(id)) {
                        l.setLabelStatus(LabelStatus.DELETED);
                    }
                }).toList();
        saveLabelsToJson(updatedLabels);
    }
}
