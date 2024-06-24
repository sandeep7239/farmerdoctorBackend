package com.farmershelper.farmerguidenceapplication.repository;

import com.farmershelper.farmerguidenceapplication.models.Comment;
import com.farmershelper.farmerguidenceapplication.models.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByQueryAndIsDeletedFalse(Query query);
    Optional<Comment> findByIdAndIsDeletedFalse(Long id);
}
