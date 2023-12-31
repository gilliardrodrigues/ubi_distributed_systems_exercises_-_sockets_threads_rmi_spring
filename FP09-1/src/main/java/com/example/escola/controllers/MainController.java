package com.example.escola.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.escola.entities.Departamento;
import com.example.escola.repositories.DepartamentoRepository;

@Controller
public class MainController {

	@Autowired
	private DepartamentoRepository depRepository;

	@GetMapping(path = "/")
	public String getAllDeps(Model model) {
		model.addAttribute("ListDeps", depRepository.findAll());
		return "index";
	}

	@GetMapping("/showNewDepForm")
	public String showNewDepForm(Model model) {
		// create model attribute to bind form data
		Departamento depar = new Departamento();
		model.addAttribute("newdep", depar);
		return "new_dep";
	}

	@PostMapping("/saveDep")
	public String saveDep(@ModelAttribute("newdep") Departamento dep) {
		// save dep to database
		depRepository.save(dep);
		return "redirect:/";
	}

	@GetMapping("/deleteDep/{id}")
	public String deleteDep(@PathVariable(value = "id") Integer id) {
		depRepository.deleteById(id);
		return "redirect:/";
	}

	@GetMapping("/showUpdateDepForm/{id}")
	public String showUpdateDepForm(@PathVariable(value = "id") Integer id, Model model) {
		Optional<Departamento> optional = depRepository.findById(id);
		Departamento dep = null;
		if (optional.isPresent()) {
			dep = optional.get();
		} else {
			throw new RuntimeException(" Department not found for id :: " + id);
		}
		// set department as a model attribute to pre-populate the form
		model.addAttribute("depart", dep);
		return "update_dep";
	}
}
