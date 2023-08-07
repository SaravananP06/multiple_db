package com.javapractice.db.multiple_db.mysql.config;


import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
@Configuration
@EnableTransactionManagement
//@PropertySources({@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true),
//        @PropertySource(value = "file:${propertyPath}/application.properties", ignoreResourceNotFound = true)
//})
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondEntityManagerFactoryBean",
        basePackages = {"com.javapractice.db.multiple_db.mysql.repo"},
        transactionManagerRef = "secondTransactionManager"
)
public class MySqlDbConfig {
    private Environment env;
    @Bean(name = "secondDataSource")
    @Primary
    public DataSource datasource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("second.datasource.url"));
        dataSource.setDriverClassName(env.getProperty("second.datasource.driver-class-name"));
        dataSource.setUsername(env.getProperty("second.datasource.username"));
        dataSource.setPassword(env.getProperty("second.datasource.password"));
        return dataSource;
    }
    @Bean(name = "secondEntityManagerFactoryBean")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(datasource());
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        props.put("hibernate.show_sql","true");
        props.put("hibernate.hbm2ddl.auto","update");
        bean.setJpaPropertyMap(props);
        bean.setPackagesToScan("com.javapractice.db.multiple_db.mysql.entity");
        return bean;
    }
    @Bean(name = "secondTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return manager;
    }
}
