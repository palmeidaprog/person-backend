package hello;

import org.hibernate.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PersonDaoDatabase implements PersonDao {
    private static final SessionFactory SESSION_FACTORY;
    private List<String> query = null;

    static {
        Configuration config = new Configuration();
        // Configure using the application resource named hibernate.cfg.xml.
        config.addAnnotatedClass(Person.class).configure();
        //config.configure();
        // Get properties from the configuration file
        Properties properties = config.getProperties();
        StandardServiceRegistryBuilder svcBuilder =
                new StandardServiceRegistryBuilder();
        svcBuilder.applySettings(properties);
        ServiceRegistry reg = svcBuilder.build();
        SESSION_FACTORY = config.buildSessionFactory(reg);
    }

    private enum Operation {
        INSERT, DELETE, UPDATE, QUERY, COMMIT_QUERY, DELETE_ID
    }

    public PersonDaoDatabase() {
        query = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    private <T> List<Person> databaseOperation(T data, Operation operation) {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = null;
        List<Person> people = null;

        if(operation == null) {
            return null;
        }

        try {
            transaction = session.beginTransaction();
            //session.delete(person);
            switch(operation) {
                case QUERY:
                    if(data instanceof String) {
                        query.add((String) data);
                    }
                    break;
                case INSERT:
                    session.save((Person) data);
                    break;
                case DELETE:
                    session.delete((Person) data);
                    break;
                case DELETE_ID:
                    if(data instanceof String) {
                        session.delete(data);
                    }
                    break;
                case UPDATE:
                    session.update(data);
                    break;
                default: // COMMIT_QUERY
                    if(data instanceof String) {
                        people = session.createQuery((String) data).list();
                    }
                    break;
            }
            transaction.commit();
        } catch(HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return people;
    }

    @Override
    public void removePerson(Person person) {
        databaseOperation(person, Operation.DELETE);
    }

    @Override
    public List<Person> getAll() {
        return commitQuery();
    }

    @Override
    public void addPerson(Person person) {
        databaseOperation(person, Operation.INSERT);
    }

    @Override
    public void updatePerson(Person person) {
        databaseOperation(person, Operation.UPDATE);
    }

    @Override
    public void getPerson(String name) {
        String[] words = name.split(" ");
        if(words.length >= 1) {
            query.add("name LIKE %" + words[0] + "%" );
        }
        for(int i = 1; i < words.length; i++) {
            query.add(" OR name LIKE %" + words[i] + "%");
        }
    }

    @Override
    public void getPersonByName(String exactName) {
        query.add("name = '" + exactName + "'");
    }

    @Override
    public void getPerson(int age) {
        query.add("age = " + age);
    }

    @Override
    public void getPerson(double height) {
        query.add("height = " + height);
    }

    @Override
    public Person getPersonById(long id) {
        query.add("id = " + id);
        List<Person> r = commitQuery();
        return r.size() >= 1 ? r.get(0) : null;
    }

    @Override
    public void getPersonTallerThan(double height) {
        query.add("height > " + height);
    }

    @Override
    public void getPersonSmallerThan(double height) {
        query.add("height < " + height);
    }

    @Override
    public void getPersonYougerThan(int age) {
        query.add("age < " + age);
    }

    @Override
    public void getPersonOlderThan(int age) {
        query.add("age > " + age);
    }

    @Override
    public List<Person> commitQuery() {
        StringBuilder queryCommit = new StringBuilder();
        queryCommit.append("FROM person");
        if (query.size() >= 1) {
            queryCommit.append(" WHERE").append(query.get(0));
            for (int i = 1; i < query.size(); i++) {
                queryCommit.append(" && ");
                queryCommit.append(query.get(i));
            }
        }
        query.clear();
        return databaseOperation(queryCommit.toString(),
                Operation.COMMIT_QUERY);
    }

    @Override
    public void removePerson(long id) {
        databaseOperation("FROM person WHERE id = " + id,
                Operation.DELETE_ID);
    }
}
