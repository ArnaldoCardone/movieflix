package com.devsuperior.movieflix.services;


import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public List<ReviewDTO> findAll() {
        List<Review> list = repository.findAll();
        return list.stream().map(e -> new ReviewDTO(e)).toList();
    }

    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {
        Review entity = new Review();
        entity.setText(dto.getText());
        //Recupera o filme
        Movie movieEnt = movieRepository.findById(dto.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado!"));;
        entity.setMovie(movieEnt);
        //Recupera o Usuário logado
        User userEnt = authService.authenticated();
        entity.setUser(userEnt);
        entity = repository.save(entity);
        return new ReviewDTO(entity);
    }

    public List<ReviewDTO> findByMovieId(Long movieId) {
        List<Review> list = repository.findByMovieId(movieId);
        return list.stream().map(e -> new ReviewDTO(e)).toList();
    }
}
