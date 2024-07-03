package com.model;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbUtil {
	private static SessionFactory sessionFactor=null;
	public static StandardServiceRegistry registry=null;
	public static SessionFactory getSessionFactory() {
		if(sessionFactor==null) {
			try {
				registry=new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
				MetadataSources sources = new MetadataSources(registry);
				Metadata metadata=sources.getMetadataBuilder().build();
				sessionFactor=metadata.getSessionFactoryBuilder().build();
			}catch(Exception e) {
				e.printStackTrace();
				if(registry!=null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
			}
		}
		return sessionFactor;
	}
}
