package com.model;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ValidationExamples {
	public static void main(String args[])
	{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		ValidatorFactory validatorFactory=Validation.buildDefaultValidatorFactory();
		Validator validator=validatorFactory.getValidator();
		System.out.println("Checking for invalid user data...");
		System.out.println("------------------------------------------");
		User invalidUser=new User(null,"a","test123",12456,"Javatechnology","db","","1234","y",-2,1,new Date(),getPastOrFutureDate(-2),getPastOrFutureDate(2),5,"sample1.com","123@");
		Set<ConstraintViolation<User>> violations=validator.validate(invalidUser);
		if(violations.isEmpty())
		{
			System.out.println("Valid user data provided");
		}
		else
		{
			System.out.println("Invalid user data found");
			for(ConstraintViolation<User>validation:violations)
			{
				System.out.println(validation.getMessage());
			}
		}
		User validUser=new User(1L,"author","a@gmail.com",16,"4","3","ML",null,"YN",2,0,getPastOrFutureDate(2),getPastOrFutureDate(1),getPastOrFutureDate(-2),2,"https://www.vsb.org","6011111111111117");
		violations=validator.validate(validUser);
					if(violations.isEmpty())
					{
						System.out.println("---------------------------------------");
						System.out.println("Valid user data provided");
					}
					else
					{
						System.out.println("Invalid user data found");
						for(ConstraintViolation<User>validation:violations)
						{
							System.out.println(validation.getMessage());
						}
					}
					System.out.println("-----------------------------");
					Transaction tr=null;
					try (Session session=HbUtil.getSessionFactory().openSession()){
					tr=session.beginTransaction();
						session.persist(validUser);
						session.persist(invalidUser);
						tr.commit();
					}
					catch(Exception e) {
						if(tr!=null)
							tr.rollback();
						e.printStackTrace();
					}
					
					
	}
	public static Date getPastOrFutureDate(int days)
	{
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
	
}
