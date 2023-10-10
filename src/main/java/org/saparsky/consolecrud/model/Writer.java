package org.saparsky.consolecrud.model;

import lombok.*;
import org.saparsky.consolecrud.enums.WriterStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private WriterStatus writerStatus;

    public void addPost(Post newPost) {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        posts.add(newPost);
    }
}
