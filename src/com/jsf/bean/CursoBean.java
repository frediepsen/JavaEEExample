package com.jsf.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;

import com.jsf.dao.Dao;
import com.jsf.model.Aluno;
import com.jsf.model.Curso;

@ManagedBean
public class CursoBean {

	private Curso curso = new Curso();
	
	private Integer cursoId;

	public Integer getCursoId() {
		return cursoId;
	}

	public void setCursoId(Integer cursoId) {
		this.cursoId = cursoId;
	}

	public Curso getCurso() {
		return curso;
	}

	public String gravar() {
		System.out.println("Gravando Curso!");
		System.out.println(this.curso.toString());

		new Dao<Curso>(Curso.class).persist(this.curso);

		this.curso = new Curso();
		
		return "aluno?faces-redirect=true";
	}
	
	public List<Curso> getCursos() {
		
		return new Dao<Curso>(Curso.class).findAll();
	}
	
	public void carregarCursoPorId() {
		
		this.curso = new Dao<Curso>(Curso.class).find(this.cursoId);
	}
}
