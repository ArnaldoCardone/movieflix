package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findByGenre(Pageable pageable, Long genreId) {
        //Caso nenhum genero tenha sido informado informa null na consulta para recuperar todos os filmes
        if (genreId == 0){
            genreId = null;
        }
        Page<MovieProjection> list = repository.searchMovies(pageable, genreId);
        return list.map(e -> new MovieCardDTO(e));
    }

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long movieId) {
        Movie result = repository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado!"));
        return new MovieDetailsDTO(result);
    }
}
