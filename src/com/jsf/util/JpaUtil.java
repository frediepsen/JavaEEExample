package com.jsf.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-jsf");

	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
}
