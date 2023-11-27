package com.skilldistillery.film.data;

import java.sql.*; 
import java.util.*;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;

public interface FilmDAO {
	Film findById(int filmId);
	List<Film> searchByKeyword(String keyword) throws SQLException;
	public Actor findActorById(int actorId) throws SQLException;
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException;
}
