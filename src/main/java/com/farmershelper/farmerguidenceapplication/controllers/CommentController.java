package com.farmershelper.farmerguidenceapplication.controllers;

import com.farmershelper.farmerguidenceapplication.models.Comment;
import com.farmershelper.farmerguidenceapplication.models.Doctor;
import com.farmershelper.farmerguidenceapplication.models.Query;
import com.farmershelper.farmerguidenceapplication.services.CommentService;
import com.farmershelper.farmerguidenceapplication.services.DoctorService;
import com.farmershelper.farmerguidenceapplication.services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {


    private final CommentService commentService;
    private final DoctorService doctorService;
    private final  QueryService queryService;

    @Autowired
    public CommentController(CommentService commentService, DoctorService doctorService, QueryService queryService) {
        this.commentService = commentService;
        this.doctorService = doctorService;
        this.queryService = queryService;
    }

    @PostMapping("/queries/{queryId}/comments")
    public ResponseEntity<?> addCommentToQuery(
            @PathVariable Long queryId,
            @RequestBody Map<String, String> request) {

        String mobileNumber = request.get("mobileNumber");
        Doctor doctor = doctorService.getDoctorByMobileNumber(mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Query query = queryService.getQueryById(queryId)
                .orElseThrow(() -> new IllegalArgumentException("Query not found"));

        Comment comment = new Comment();
        comment.setContent(request.get("content"));
        comment.setDoctor(doctor);
        comment.setQuery(query);

        Comment savedComment = commentService.addComment(comment);

        return ResponseEntity.ok(savedComment);
    }

}
