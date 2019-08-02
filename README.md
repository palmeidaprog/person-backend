# Person Backend 
Simple Backend that manages a MySQL database with one table Person using and Hibernate and Spring for RESTful implementation

## Important:

For this project to work it is important to have a hibernate.cfg.xml and/or a class annotated with Configuration to be able to properly connect with a MySQL server. Like the following example:

### 1 - Using a class:

```java
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class SpringHibernateConfig {
    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
                //.driverClassName("com.mysql.cj.jdbc.Driver")
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://hostaname_here/database_here")
                .username("username_here")
                .password("password_here")
                .build();
    }
}
```

### 2 - Using hibernate.cfg.xml file inside src/main/resources:

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <!-- Create table if doesn't exists -->
        <property name="hibernate.hbm2ddl.auto">update</property>
        <!-- Choose database connector -->
        <property name="hibernate.connection.driver_class">
            com.mysql.jdbc.Driver</property>
        <!-- Indicate to the database to generate suitable SQL -->
        <property name="hibernate.dialect">
            org.hibernate.dialect.MySQL5Dialect</property>
        <!-- URL to the database -->
        <property name="hibernate.connection.url">
            jdbc:mysql://url/database_name</property>
        <property name="hibernate.connection.username">username_here
            </property>
        <property name="hibernate.connection.password">password_here</property>
        <!-- Class to be mapped -->
        <mapping class="hello.Person"></mapping>
    </session-factory>
</hibernate-configuration>
```