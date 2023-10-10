package org.saparsky.consolecrud.model;

import lombok.*;
import org.saparsky.consolecrud.enums.PostStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class Post {
    private Long id;
    private String content;
    private Date created;
    private Date updated;
    private List<Label> labels;
    private PostStatus postStatus;

    public void addLabel(Label label) {
        if (labels == null) {
            labels = new ArrayList<>();
        }
        labels.add(label);
    }
}
