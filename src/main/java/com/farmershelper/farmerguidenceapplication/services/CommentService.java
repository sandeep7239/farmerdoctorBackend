package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.models.Comment;
import com.farmershelper.farmerguidenceapplication.models.Query;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment addComment(Comment comment);
    Comment updateComment(Long commentId, String content);
    void deleteComment(Long commentId);
    List<Comment> getCommentsByQuery(Query query);
    Optional<Comment> getCommentById(Long commentId);
}
