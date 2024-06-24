package com.farmershelper.farmerguidenceapplication.controllers;

import com.farmershelper.farmerguidenceapplication.models.Comment;
import com.farmershelper.farmerguidenceapplication.models.Doctor;
import com.farmershelper.farmerguidenceapplication.models.Query;
import com.farmershelper.farmerguidenceapplication.services.CommentService;
import com.farmershelper.farmerguidenceapplication.services.DoctorService;
import com.farmershelper.farmerguidenceapplication.services.QueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private QueryService queryService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void addCommentToQuery() throws Exception {
        Doctor doctor = new Doctor();
        Query query = new Query();
        Comment comment = new Comment();

        when(doctorService.getDoctorByMobileNumber(anyString())).thenReturn(Optional.of(doctor));
        when(queryService.getQueryById(anyLong())).thenReturn(Optional.of(query));
        when(commentService.addComment(any(Comment.class))).thenReturn(comment);

        mockMvc.perform(post("/api/comments/queries/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mobileNumber\":\"9876543210\", \"content\":\"Test comment\"}"))
                .andExpect(status().isOk());
    }
}
