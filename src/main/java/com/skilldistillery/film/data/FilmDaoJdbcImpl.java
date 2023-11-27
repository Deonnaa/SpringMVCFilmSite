package com.skilldistillery.film.data;

import java.sql.*;
import java.util.*;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;

public class FilmDaoJdbcImpl implements FilmDAO {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Eastern";
	private static final String USER = "student";
	private static final String PWD = "student";

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Error loading MySQL Driver");
			throw new RuntimeException("Unable to load MySQL Driver class");
		}
	}

	@Override
	public Film findById(int filmId) {
		Film film = null;
		String sql = "SELECT film.*, language.name AS language_name, category.name AS category FROM film JOIN language ON film.language_id = language.id JOIN film_category ON film.id = film_category.film_id JOIN category ON film_category.category_id = category.id WHERE film.id = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PWD); // Specify your database
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				film = new Film();
				film.setFilmId(rs.getInt("id"));
				film.setTitle(rs.getString("title"));
				film.setDesc(rs.getString("description"));
				film.setReleaseYear(rs.getShort("release_year"));
				film.setLangId(rs.getInt("language_id"));
				film.setRentDur(rs.getInt("rental_duration"));
				film.setRate(rs.getDouble("rental_rate"));
				film.setLength(rs.getInt("length"));
				film.setRepCost(rs.getDouble("replacement_cost"));
				film.setRating(rs.getString("rating"));
				film.setFeatures(rs.getString("special_features"));
				film.setLanguage(rs.getString("language_name"));
				film.setCategory(rs.getString("category"));

				film.setActors(findActorsByFilmId(filmId));
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle exception
		}
		return film;
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		Connection conn = DriverManager.getConnection(URL, USER, PWD);

		String sql = "SELECT * FROM actor WHERE id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);

		ResultSet actorResult = stmt.executeQuery();

		if (actorResult.next()) {
			String fn = actorResult.getString("first_name");
			String ln = actorResult.getString("last_name");
			actor = new Actor(fn, ln);

			actor.setId(actorResult.getInt("id"));

			List<Film> theFilms = findFilmsByActorId(actorId);
			actor.setFilms(theFilms);
		}
		conn.close();
		return actor;
	}

	public List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PWD);
			String sql = "SELECT film.*, category.name AS category FROM film JOIN film_actor ON film.id = film_actor.film_id JOIN film_category ON film.id = film_category.film_id JOIN category ON film_category.category_id = category.id WHERE film_actor.actor_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int filmId = rs.getInt("id");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				short releaseYear = rs.getShort("release_year");
				int langId = rs.getInt("language_id");
				int rentDur = rs.getInt("rental_duration");
				double rate = rs.getDouble("rental_rate");
				int length = rs.getInt("length");
				double repCost = rs.getDouble("replacement_cost");
				String rating = rs.getString("rating");
				String features = rs.getString("special_features");
				String category = rs.getString("category");
				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features, category);
				films.add(film);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		List<Actor> actors = new ArrayList<>();

		try {
			Connection conn = DriverManager.getConnection(URL, USER, PWD);
			String sql = "SELECT actor.id, actor.first_name, actor.last_name FROM actor JOIN film_actor ON actor.id = film_actor.actor_id WHERE film_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Actor actor = new Actor();
				actor.setId(rs.getInt("id"));
				actor.setFirstName(rs.getString("first_name"));
				actor.setLastName(rs.getString("last_name"));

				actors.add(actor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

	@Override
	public List<Film> searchByKeyword(String keyword) throws SQLException {
		List<Film> films = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PWD);
			String sql = "SELECT film.*, language.name AS language_name, category.name AS category FROM film JOIN language ON film.language_id = language.id JOIN film_category ON film.id = film_category.film_id JOIN category ON film_category.category_id = category.id WHERE title LIKE ? OR description LIKE ? OR category.name LIKE ?";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			stmt.setString(3, "%" + keyword + "%");

			rs = stmt.executeQuery();

			while (rs.next()) {
				Film film = new Film();
				film.setFilmId(rs.getInt("id"));
				film.setTitle(rs.getString("title"));
				film.setDesc(rs.getString("description"));
				film.setReleaseYear(rs.getShort("release_year"));
				film.setLangId(rs.getInt("language_id"));
				film.setRating(rs.getString("rating"));
				film.setLanguage(rs.getString("language_name"));
				film.setCategory(rs.getString("category"));

				film.setActors(findActorsByFilmId(film.getFilmId()));
				films.add(film);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			// Ensure resources are closed in finally block
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return films;
	}

}
