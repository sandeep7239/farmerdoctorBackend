package com.farmershelper.farmerguidenceapplication.controllers;

import com.farmershelper.farmerguidenceapplication.models.Comment;
import com.farmershelper.farmerguidenceapplication.models.Farmer;
import com.farmershelper.farmerguidenceapplication.models.Query;
import com.farmershelper.farmerguidenceapplication.services.CommentService;
import com.farmershelper.farmerguidenceapplication.services.FarmerService;
import com.farmershelper.farmerguidenceapplication.services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/farmer")
@CrossOrigin(origins = "http://localhost:3000")
public class FarmerController {

    private final FarmerService farmerService;
    private final QueryService queryService;
    private final CommentService commentService;

    @Autowired
    public FarmerController(FarmerService farmerService, QueryService queryService, CommentService commentService) {
        this.farmerService = farmerService;
        this.queryService = queryService;
        this.commentService = commentService;
    }

    private boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber.matches("^[6-9][0-9]{9}$");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Farmer farmer) {
        if (!isValidMobileNumber(farmer.getMobileNumber())) {
            return ResponseEntity.badRequest().body("Invalid mobile number. Must be exactly 10 digits.");
        }
        try {
            Farmer registeredFarmer = farmerService.register(farmer);
            return ResponseEntity.ok(registeredFarmer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String mobileNumber = request.get("mobileNumber");
        if (!isValidMobileNumber(mobileNumber)) {
            return ResponseEntity.badRequest().body("Invalid phone number: Must be exactly 10 digits");
        }
        Optional<Farmer> farmerOptional = farmerService.getFarmerByMobileNumber(mobileNumber);
        if (farmerOptional.isPresent()) {
            String otp = farmerService.generateOtp();
            Farmer farmer = farmerOptional.get();
            farmerService.saveOtp(farmer, otp);
            return ResponseEntity.ok("OTP sent to your mobile number");
        } else {
            return ResponseEntity.status(404).body("Farmer not found. Kindly do register");
        }
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOtp(@RequestBody ValidateOtpRequest request) {
        if (!isValidMobileNumber(request.getMobileNumber())) {
            return ResponseEntity.badRequest().body("Invalid phone number: Must be exactly 10 digits");
        }
        Optional<Farmer> farmerOptional = farmerService.getFarmerByMobileNumber(request.getMobileNumber());
        if (farmerOptional.isPresent()) {
            Farmer farmer = farmerOptional.get();
            if (farmerService.validateOtp(farmer, request.getOtp())) {
                farmerService.clearOtp(farmer);
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid OTP");
            }
        } else {
            return ResponseEntity.status(404).body("Farmer not found");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody Map<String, String> request) {
        String mobileNumber = request.get("mobileNumber");
        if (!isValidMobileNumber(mobileNumber)) {
            return ResponseEntity.badRequest().body("Invalid phone number: Must be exactly 10 digits");
        }
        Optional<Farmer> farmerOptional = farmerService.getFarmerByMobileNumber(mobileNumber);
        if (farmerOptional.isPresent()) {
            Farmer farmer = farmerOptional.get();
            farmerService.clearOtp(farmer);
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.status(404).body("Farmer not found");
        }
    }

    @PostMapping("/query")
    public ResponseEntity<?> submitQuery(@RequestParam String mobileNumber, @RequestBody Map<String, String> request) {
        if (!isValidMobileNumber(mobileNumber)) {
            return ResponseEntity.badRequest().body("Invalid phone number: Must be exactly 10 digits");
        }
        try {
            Optional<Farmer> farmerOptional = farmerService.getFarmerByMobileNumber(mobileNumber);
            if (farmerOptional.isPresent()) {
                Farmer farmer = farmerOptional.get();
                Query query = new Query();
                query.setDescription(request.get("description"));
                query.setFarmer(farmer);
                Query savedQuery = farmerService.saveQuery(query);
                return ResponseEntity.ok(savedQuery);
            } else {
                return ResponseEntity.status(404).body("Farmer not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/queries")
    public ResponseEntity<List<Query>> getAllQueries(@RequestParam String mobileNumber) {
        Optional<Farmer> farmerOptional = farmerService.getFarmerByMobileNumber(mobileNumber);
        if (farmerOptional.isPresent()) {
            Farmer farmer = farmerOptional.get();
            List<Query> queries = farmerService.getQueriesByFarmer(farmer);
            if (queries.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(queries);
            }
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/query/{queryId}")
    public ResponseEntity<?> updateQuery(@PathVariable Long queryId, @RequestBody Query queryDetails) {
        try {
            Query updatedQuery = farmerService.updateQuery(queryId, queryDetails);
            return ResponseEntity.ok(updatedQuery);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/query/{queryId}")
    public ResponseEntity<?> deleteQuery(@PathVariable Long queryId) {
        try {
            farmerService.deleteQuery(queryId);
            return ResponseEntity.ok("Query deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/queries/{queryId}/comments")
    public List<Comment> getCommentsForQuery(@PathVariable Long queryId) {
        Query query = queryService.getQueryById(queryId)
                .orElseThrow(() -> new IllegalArgumentException("Query not found"));
        return commentService.getCommentsByQuery(query);
    }
}
