package com.jsf.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.OptimisticLockException;

import com.jsf.dao.Dao;
import com.jsf.model.Aluno;
import com.jsf.model.Curso;

@ManagedBean
@ViewScoped
public class AlunoBean {

	private Aluno aluno = new Aluno();

	private Integer idCurso;

	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}

	public Integer getIdCurso() {
		return idCurso;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public List<Curso> getCursos() {

		return new Dao<Curso>(Curso.class).findAll();
	}

	public void relacionarCurso() {

		Curso cursoSelecionadoNaTela = new Dao<Curso>(Curso.class).find(this.idCurso);
		this.aluno.adicionarCurso(cursoSelecionadoNaTela); // relacionando aluno
															// e curso
	}

	public void gravar() {
		System.out.println("Gravando Aluno!");
		System.out.println(this.aluno.toString());

		if (this.aluno.getCursos().isEmpty()) {
			// throw new RuntimeException("Aluno deve estar matriculado pelo
			// menos em um curso!");
			FacesContext.getCurrentInstance().addMessage("curso",
					new FacesMessage("Aluno deve estar matriculado pelo menos em um curso!"));
			return;
		}

		try {

			if (this.aluno.getId() == null) {
				System.out.println("chamou o persist");
				new Dao<Aluno>(Aluno.class).persist(this.aluno);
			} else {
				System.out.println("chamou o merge");
				new Dao<Aluno>(Aluno.class).merge(this.aluno);
			}

		} catch (OptimisticLockException ole) {
			FacesContext.getCurrentInstance().addMessage("aluno", new FacesMessage(
					"O registro foi atualizado por outra transação. Atualize a tela para carregar os dados atuais."));
			return;
		}

		this.aluno = new Aluno();
	}

	public List<Aluno> getAlunos() {

		return new Dao<Aluno>(Aluno.class).findAll();
	}

	public void deveIniciarComS(FacesContext contextoDaView, UIComponent componenteDaView, Object valorDigitadoNoCampo)
			throws ValidatorException {

		String valor = valorDigitadoNoCampo.toString();

		if (!valor.startsWith("S")) {
			throw new ValidatorException(new FacesMessage("Deve começar com S maiúsculo"));
		}
	}

	public void remover(Aluno aluno) {

		new Dao<Aluno>(Aluno.class).remove(aluno);
	}

	public void carregar(Aluno aluno) {

		this.aluno = aluno;
	}

	public void removerCursoDoAluno(Curso curso) {

		this.aluno.removerCurso(curso);
	}
}
