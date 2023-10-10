package org.saparsky.consolecrud.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.saparsky.consolecrud.enums.PostStatus;
import org.saparsky.consolecrud.exceptions.NotFoundException;
import org.saparsky.consolecrud.model.Label;
import org.saparsky.consolecrud.model.Post;
import org.saparsky.consolecrud.repository.PostRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonPostRepositoryImpl implements PostRepository {

    private final String FILE_PATH = "src/main/resources/posts.json";
    private final Gson gson = new Gson();

    private final GsonLabelRepositoryImpl labelRepository = new GsonLabelRepositoryImpl();

    private List<Post> getPostsFromJson() {
        try (FileReader postsJson = new FileReader(FILE_PATH)) {
            Type targetType = new TypeToken<ArrayList<Post>>(){}.getType();
            return gson.fromJson(postsJson, targetType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void savePostsToJson(List<Post> updatedPost) {
        try (FileWriter postsJson = new FileWriter(FILE_PATH)) {
            gson.toJson(updatedPost, postsJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Post getById(Long id) {
        return getPostsFromJson().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Post with id %s not found".formatted(id)));
    }

    @Override
    public List<Post> getAll() {
        return getPostsFromJson();
    }

    private Long getNextId(List<Post> postList) {
        return postList.stream()
                .map(Post::getId)
                .max(Long::compare)
                .orElse(0L) + 1;
    }

    @Override
    public Post save(Post post) {
        List<Post> postList = getPostsFromJson();
        Long nextId = getNextId(postList);
        post.setId(nextId);
        postList.add(post);
        savePostsToJson(postList);
        return post;
    }


    @Override
    public Post update(Post post) {
        List<Post> postList = getPostsFromJson();
        List<Post> updatedPost = postList.stream()
                .peek(existingPost -> {
                    if (existingPost.getId().equals(post.getId())) {
                        existingPost.setContent(post.getContent());
                        existingPost.setUpdated(post.getUpdated());
                        existingPost.setPostStatus(post.getPostStatus());

                        if (post.getLabels() != null) {
                            for (Label label: post.getLabels()) {
                                if (!existingPost.getLabels().contains(label)) {
                                    labelRepository.save(label);
                                }
                            }
                            existingPost.setLabels(post.getLabels());
                        }
                    }
                }).toList();
        savePostsToJson(updatedPost);
        return post;

    }

    @Override
    public void deleteById(Long id) {
        List<Post> postList = getPostsFromJson();
        List<Post> updatedPost = postList.stream()
                .peek(post -> {
                    if (post.getId().equals(id)) {
                        post.setPostStatus(PostStatus.DELETED);
                    }
                }).toList();
        savePostsToJson(updatedPost);
    }


}
