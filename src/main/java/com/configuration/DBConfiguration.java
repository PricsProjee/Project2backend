package com.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.model.BlogComment;
import com.model.BlogPost;
import com.model.BlogPostLikes;
import com.model.Friend;
import com.model.Job;
import com.model.Notification;
import com.model.ProfilePic;
import com.model.User;

@Configuration
@EnableTransactionManagement

public class DBConfiguration {
	public DBConfiguration() {
		System.out.println("DBConfiguration is instantiated ");
	}

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder lsfb = new LocalSessionFactoryBuilder(getDataSource());
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		lsfb.addProperties(hibernateProperties);
		Class classes[] = new Class[] { User.class, Job.class, BlogPost.class, Notification.class, BlogPostLikes.class,
				BlogComment.class, ProfilePic.class ,Friend.class };
		return lsfb.addAnnotatedClasses(classes).buildSessionFactory();
	}

	@Bean
	public DataSource getDataSource() {
		BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		datasource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		datasource.setUsername("Project2DB");
		datasource.setPassword("proj@dt");
		return datasource;
	}

	@Bean
	public HibernateTransactionManager hbtm() {
		return new HibernateTransactionManager(sessionFactory());
	}
}
