package org.saparsky.consolecrud.controller;

import org.saparsky.consolecrud.enums.PostStatus;
import org.saparsky.consolecrud.model.Label;
import org.saparsky.consolecrud.model.Post;
import org.saparsky.consolecrud.repository.gson.GsonPostRepositoryImpl;

import java.util.Date;
import java.util.List;

public class PostController {

    private final GsonPostRepositoryImpl postRepository;
    public PostController(GsonPostRepositoryImpl postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Post getPostWithId(Long id) {
        return postRepository.getById(id);
    }

    public Post create(String content, Date created, Date updated, List<Label> labels, PostStatus postStatus) {
        Post post = Post.builder()
                .content(content)
                .created(created)
                .updated(updated)
                .labels(labels)
                .postStatus(postStatus)
                .build();
        return postRepository.save(post);
    }

    public void update(Long id, String content, Date updated, PostStatus postStatus) {
        Post updatedPost = Post.builder()
                .id(id)
                .content(content)
                .updated(updated)
                .postStatus(postStatus)
                .build();
        postRepository.update(updatedPost);
    }

    public Post addLabelToPost(Long idPost, Label label) {
        Post post = postRepository.getById(idPost);
        post.addLabel(label);
        return postRepository.update(post);
    }
}
