package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;


    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_VISITOR')")
    @GetMapping()
    public ResponseEntity<List<ReviewDTO>> findAll() {

        List<ReviewDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @PostMapping()
    public ResponseEntity<List<ReviewDTO>> insertReview(@RequestBody @Valid ReviewDTO dto) {

        dto = service.insert(dto);

        List<ReviewDTO> list = service.findByMovieId(dto.getMovieId());
        return ResponseEntity.ok().body(list);
    }
}
