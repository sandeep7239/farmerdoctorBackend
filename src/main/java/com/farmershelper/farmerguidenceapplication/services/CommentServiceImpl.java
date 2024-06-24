package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.models.Comment;
import com.farmershelper.farmerguidenceapplication.models.Query;
import com.farmershelper.farmerguidenceapplication.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long commentId, String content) {
        Optional<Comment> commentOptional = commentRepository.findByIdAndIsDeletedFalse(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setContent(content);
            return commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("Comment not found");
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        Optional<Comment> commentOptional = commentRepository.findByIdAndIsDeletedFalse(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setDeleted(true);
            commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("Comment not found");
        }
    }

    @Override
    public List<Comment> getCommentsByQuery(Query query) {
        return commentRepository.findByQueryAndIsDeletedFalse(query);
    }

    @Override
    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findByIdAndIsDeletedFalse(commentId);
    }
}
