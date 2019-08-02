package hello;

import java.util.List;

public interface PersonDao {
    void addPerson(Person person);
    void removePerson(Person person);
    void removePerson(long id);
    void updatePerson(Person person);
    void getPerson(String name);
    void getPerson(int age);
    void getPerson(double height);
    void getPersonByName(String exactName);
    Person getPersonById(long id);
    void getPersonTallerThan(double height);
    void getPersonSmallerThan(double height);
    void getPersonYougerThan(int age);
    void getPersonOlderThan(int age);
    void getPersonByCpf(String cpf);
    List<Person> commitQuery();
    List<Person> getAll();
}
