package org.example.jspcafe.post.service;

import org.example.jspcafe.Component;
import org.example.jspcafe.post.model.Post;
import org.example.jspcafe.post.repository.InMemoryPostRepository;
import org.example.jspcafe.post.repository.PostRepository;
import org.example.jspcafe.post.request.PostCreateRequest;
import org.example.jspcafe.post.response.PostListResponse;
import org.example.jspcafe.post.response.PostResponse;
import org.example.jspcafe.user.model.Nickname;
import org.example.jspcafe.user.model.User;
import org.example.jspcafe.user.repository.InMemoryUserRepository;
import org.example.jspcafe.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void createPost(PostCreateRequest request) {
        final Long userId = request.userId();
        final String title = request.title();
        final String content = request.content();

        Post post = new Post(userId, title, content, LocalDateTime.now());

        postRepository.save(post);
    }

    public PostListResponse getPosts(int page, int size) {
        final List<Post> posts = postRepository.findAll(page, size);
        final Set<Long> collectUserId = posts.stream()
                .map(post -> post.getUserId())
                .collect(Collectors.toUnmodifiableSet());

        final Map<Long, Nickname> nicknameMap = userRepository.findAllById(collectUserId).stream()
                .collect(Collectors.toUnmodifiableMap(User::getUserId, User::getNickname));

        final List<PostResponse> postList = posts.stream()
                .map(post -> new PostResponse(
                        post.getPostId(),
                        nicknameMap.get(post.getUserId()).getValue(),
                        post.getTitle().getValue(),
                        post.getContent().getValue(),
                        post.getCreatedAt())
                ).toList();

        final int totalElements = postRepository.count();

        return PostListResponse.of(totalElements, postList);
    }


    public PostService(
            InMemoryPostRepository postRepository,
            InMemoryUserRepository userRepository
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
}
