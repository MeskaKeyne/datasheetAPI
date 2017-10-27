package be.ste.st.datasheet.rest.service;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import be.steformations.java_data.timesheets.service.TimesheetsDataService;
import be.steformations.pc.timesheets.dao.service.jpa.TimesheetsJpaDataServiceImpl;

public class DaoRestFactory {
	
	private static DaoRestFactory instance = new DaoRestFactory();
	private TimesheetsDataService dao;
	protected EntityManager entityManager;
	
	private DaoRestFactory() {
		super();
		this.entityManager =Persistence.createEntityManagerFactory("postgresql_connect").createEntityManager();
		this.dao = new TimesheetsJpaDataServiceImpl(this.entityManager);
	}
	public static DaoRestFactory getInstance() {return DaoRestFactory.instance;}
	public TimesheetsDataService getDao() {return this.dao;}
	
	
}
