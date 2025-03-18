package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value = """
	SELECT DISTINCT id, title, sub_title as subTitle, movie_year as movieYear, img_url as imgUrl
	FROM tb_movie
	WHERE (:genreId IS NULL OR genre_id = :genreId)
	order by title
	""",
            countQuery = """
	SELECT COUNT(*) FROM (
	SELECT DISTINCT id, title, sub_title, movie_year, img_url
	FROM tb_movie
	WHERE (:genreId IS NULL OR genre_id = :genreId)
	) AS tb_result
	""")
    Page<MovieProjection> searchMovies(Pageable pageable, Long genreId);
}
