package com.jsf.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.jsf.util.JpaUtil;

public class Dao<T> {

	private final Class<T> classe;

	public Dao(Class<T> classe) {
		this.classe = classe;
	}
	
	public void persist(T t) {
		
		EntityManager em = new JpaUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(t);
		
		em.getTransaction().commit();
		
		em.close();
	}
	
	public void merge(T t) {
		
		EntityManager em = new JpaUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		em.merge(t);
		
		em.getTransaction().commit();
		
		em.close();
	}
	
	public void remove(T t) {
		
		EntityManager em = new JpaUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		em.remove(em.merge(t));
		
		em.getTransaction().commit();
		
		em.close();
	}
	
	public T find(Integer id) {
		
		EntityManager em = new JpaUtil().getEntityManager();
		
		T objeto = em.find(classe, id);
		em.close();
		
		return objeto;
	}
	
	public List<T> findAll() {
		
		EntityManager em = new JpaUtil().getEntityManager();
		
		String nomeEntidade = this.classe.getName();
		
		StringBuilder jpql = new StringBuilder();
		jpql.append("select t from ");
		jpql.append(nomeEntidade);
		jpql.append(" t");
		
		TypedQuery<T> typedQuery = em.createQuery(jpql.toString(), classe);
		
		List<T> lista = typedQuery.getResultList();
		em.close();
		
		return lista;
	}
}
