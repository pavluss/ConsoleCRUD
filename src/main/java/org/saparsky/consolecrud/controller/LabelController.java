package org.saparsky.consolecrud.controller;

import org.saparsky.consolecrud.enums.LabelStatus;
import org.saparsky.consolecrud.model.Label;
import org.saparsky.consolecrud.repository.gson.GsonLabelRepositoryImpl;

import java.util.List;

public class LabelController {
    private final GsonLabelRepositoryImpl labelRepository;
    public LabelController(GsonLabelRepositoryImpl labelRepository) {
        this.labelRepository = labelRepository;
    }

    public List<Label> getAllLabels() {
        return labelRepository.getAll();
    }

    public void delete(Long id) {
        labelRepository.deleteById(id);
    }

    public Label create(String name, LabelStatus labelStatus) {
        Label newLabel = new Label(name, labelStatus);
        return labelRepository.save(newLabel);
    }

    public Label update(Long id, String name, LabelStatus labelStatus) {
        Label updateLabel = new Label(name, labelStatus);
        updateLabel.setId(id);
        return labelRepository.update(updateLabel);
    }

    public Label getLabelWithId(Long id) {
        return labelRepository.getById(id);
    }
}
