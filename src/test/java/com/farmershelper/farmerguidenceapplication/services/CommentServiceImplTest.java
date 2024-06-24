package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.models.Comment;
import com.farmershelper.farmerguidenceapplication.models.Query;
import com.farmershelper.farmerguidenceapplication.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addComment() {
        Comment comment = new Comment();
        when(commentRepository.save(comment)).thenReturn(comment);
        Comment result = commentService.addComment(comment);
        assertEquals(comment, result);
    }

    @Test
    void updateComment() {
        Comment comment = new Comment();
        comment.setContent("Updated Content");
        when(commentRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment result = commentService.updateComment(1L, "Updated Content");
        assertEquals("Updated Content", result.getContent());
    }

    @Test
    void deleteComment() {
        Comment comment = new Comment();
        when(commentRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(comment));

        commentService.deleteComment(1L);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void getCommentsByQuery() {
        Query query = new Query();
        commentService.getCommentsByQuery(query);
        verify(commentRepository, times(1)).findByQueryAndIsDeletedFalse(query);
    }

}
