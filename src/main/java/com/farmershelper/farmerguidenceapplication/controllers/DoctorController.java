package com.farmershelper.farmerguidenceapplication.controllers;

import com.farmershelper.farmerguidenceapplication.models.Comment;
import com.farmershelper.farmerguidenceapplication.models.Doctor;
import com.farmershelper.farmerguidenceapplication.models.Query;
import com.farmershelper.farmerguidenceapplication.services.CommentService;
import com.farmershelper.farmerguidenceapplication.services.DoctorService;
import com.farmershelper.farmerguidenceapplication.services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin(origins = "http://localhost:3000")
public class DoctorController {

    private final DoctorService doctorService;
    private final QueryService queryService;
    private final CommentService commentService;

    @Autowired
    public DoctorController(DoctorService doctorService, QueryService queryService, CommentService commentService) {
        this.doctorService = doctorService;
        this.queryService = queryService;
        this.commentService = commentService;
    }

    private boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber.matches("^[6-9][0-9]{9}$");
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody Doctor doctor) {
        if (!isValidMobileNumber(doctor.getMobileNumber())) {
            return ResponseEntity.badRequest().body("Invalid mobile number. Must be exactly 10 digits.");
        }
        try {
            Doctor registeredDoctor = doctorService.register(doctor);
            return ResponseEntity.ok(registeredDoctor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String mobileNumber = request.get("mobileNumber");
        if (!isValidMobileNumber(mobileNumber)) {
            return ResponseEntity.badRequest().body("Invalid phone number: Must be exactly 10 digits");
        }
        Optional<Doctor> doctorOptional = doctorService.getDoctorByMobileNumber(mobileNumber);
        if (doctorOptional.isPresent()) {
            String otp = doctorService.generateOtp();
            Doctor doctor = doctorOptional.get();
            doctorService.saveOtp(doctor, otp);
            return ResponseEntity.ok("OTP sent to your mobile number");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found. Kindly do register");
        }
    }

    @PostMapping(value = "/validate-otp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> validateOtp(@RequestBody ValidateOtpRequest request) {
        if (!isValidMobileNumber(request.getMobileNumber())) {
            return ResponseEntity.badRequest().body("Invalid phone number: Must be exactly 10 digits");
        }
        Optional<Doctor> doctorOptional = doctorService.getDoctorByMobileNumber(request.getMobileNumber());
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            if (doctorService.validateOtp(doctor, request.getOtp())) {
                doctorService.clearOtp(doctor);
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }
    }

    @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> logout(@RequestBody Map<String, String> request) {
        String mobileNumber = request.get("mobileNumber");
        if (!isValidMobileNumber(mobileNumber)) {
            return ResponseEntity.badRequest().body("Invalid phone number: Must be exactly 10 digits");
        }
        Optional<Doctor> doctorOptional = doctorService.getDoctorByMobileNumber(mobileNumber);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            doctorService.clearOtp(doctor);
            return ResponseEntity.ok("Logged out successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }
    }

    @GetMapping("/queries")
    public ResponseEntity<List<Query>> getAllQueries() {
        List<Query> queries = queryService.getAllQueries();
        return ResponseEntity.ok(queries);
    }

    @PostMapping(value = "/queries/{queryId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCommentToQuery(
            @PathVariable Long queryId,
            @RequestBody Map<String, String> request) {

        String mobileNumber = request.get("mobileNumber");
        String content = request.get("content");

        Doctor doctor = doctorService.getDoctorByMobileNumber(mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Query query = queryService.getQueryById(queryId)
                .orElseThrow(() -> new IllegalArgumentException("Query not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setDoctor(doctor);
        comment.setQuery(query);

        Comment savedComment = commentService.addComment(comment);

        return ResponseEntity.ok(savedComment);
    }

    @PutMapping(value = "/comments/{commentId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody Map<String, String> request) {
        String content = request.get("content");
        String mobileNumber = request.get("mobileNumber");

        Doctor doctor = doctorService.getDoctorByMobileNumber(mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Comment comment = commentService.getCommentById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!comment.getDoctor().getId().equals(doctor.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only update your own comments");
        }

        Comment updatedComment = commentService.updateComment(commentId, content);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@RequestParam String mobileNumber, @PathVariable Long commentId) {
        if (!isValidMobileNumber(mobileNumber)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid mobile number");
        }
        Doctor doctor = doctorService.getDoctorByMobileNumber(mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Comment comment = commentService.getCommentById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!comment.getDoctor().getId().equals(doctor.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own comments");
        }

        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }

    @GetMapping("/queries/sorted")
    public ResponseEntity<List<Query>> getSortedQueries() {
        List<Query> queries = queryService.getQueriesSortedByCommentStatus();
        return ResponseEntity.ok(queries);
    }
}
