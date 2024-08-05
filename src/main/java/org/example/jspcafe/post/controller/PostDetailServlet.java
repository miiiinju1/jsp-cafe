package org.example.jspcafe.post.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.jspcafe.comment.service.CommentService;
import org.example.jspcafe.di.ApplicationContext;
import org.example.jspcafe.post.response.CommentResponse;
import org.example.jspcafe.post.response.PostResponse;
import org.example.jspcafe.post.service.PostService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PostDetailServlet", urlPatterns = "/posts/*")
public class PostDetailServlet extends HttpServlet {

    private PostService postService;
    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.postService = ApplicationContext.getContainer().resolve(PostService.class);
        this.commentService = ApplicationContext.getContainer().resolve(CommentService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        HttpSession session = req.getSession();
        if (session.getAttribute("isLogined") == null || !(boolean) session.getAttribute("isLogined")) {
            resp.sendRedirect("/login");
            return;
        }

        String postIdStr = pathInfo.substring(1);
        PostResponse post = postService.getPost(postIdStr);

        Long postId = Long.parseLong(postIdStr);

        List<CommentResponse> comments = commentService.findCommentsJoinUser(postId, 0, 5).comments();

        String commentWriter = (String) req.getSession().getAttribute("nickname");
        if(commentWriter== null) {
            commentWriter = "댓글을 작성하려면 로그인이 필요합니다.";
        }

        req.setAttribute("post", post);
        req.setAttribute("commentWriter", commentWriter);
        req.setAttribute("comments", comments);

        req.getRequestDispatcher("/post/post.jsp").forward(req, resp);
    }
}
