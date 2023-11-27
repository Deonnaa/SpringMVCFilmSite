package com.skilldistillery.film.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.data.FilmDAO;
import com.skilldistillery.film.entities.Film;

@Controller
public class FilmController {

	@Autowired
	private FilmDAO filmDao;

	@RequestMapping(path = { "home.do", "/" })
	public String goToHome() {
		return "WEB-INF/home.jsp";
	}

	@RequestMapping(path = "searchById.do", params = "filmId", method = RequestMethod.GET)
	public String searchById(int filmId, Model model) {
		Film film = filmDao.findById(filmId);
		model.addAttribute("film", film);
		return "WEB-INF/filmDetail.jsp";
	}

	@RequestMapping(path = "searchByKeyword.do", params = "keyword", method = RequestMethod.GET)
	public String searchByKeyword(String keyword, Model model) throws SQLException {
		List<Film> films = filmDao.searchByKeyword(keyword);
		System.out.println("Film List Size: " + films.size());
		model.addAttribute("filmList", films);
		return "WEB-INF/filmList.jsp";
	}

}
