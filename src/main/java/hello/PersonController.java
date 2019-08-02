package hello;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonController {
    private PersonDao dao;

    public PersonController() {
        dao = PersonDaoFactory.getInstance();
    }

    @RequestMapping("/addPerson")
    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public void addPerson(
            @RequestParam(value="name", defaultValue="") String name,
            @RequestParam(value="age", defaultValue="-1") int age,
            @RequestParam(value="height", defaultValue="-1") double height,
            @RequestParam(value="cpf", defaultValue="") String cpf) throws
            PersonException {

        if(name.trim().isEmpty()) {
            throw new PersonException("Can't create a person without a name");
        }
        Person person = new Person(name);
        if(age != -1) {
            person.setAge(age);
        }

        if(height != -1) {
            person.setHeight(height);
        }

        if(!cpf.trim().isEmpty()) {
            person.setCpf(cpf);
        }
        dao.addPerson(person);
    }

    @RequestMapping("/deletePerson")
    public void removePerson(
            @RequestParam(value="id", defaultValue="-1") long id) {
        if(id != -1) {
            dao.removePerson(id);
        }
    }

    @RequestMapping("/editPerson")
    @ExceptionHandler
    public void editPerson(
            @RequestParam(value="id", defaultValue="-1") long id,
            @RequestParam(value="name", defaultValue="") String name,
            @RequestParam(value="age", defaultValue="-1") int age,
            @RequestParam(value="height", defaultValue="-1") double height,
            @RequestParam(value="cpf", defaultValue="") String cpf) throws
            PersonException {
        if(id == -1) {
            throw new PersonException("ID required to update Person");
        }

        Person person = dao.getPersonById(id);
        if(person == null) {
            throw new PersonException("Invalid ID");
        }

        if(!name.trim().isEmpty()) {
            person.setName(name);
        }

        if(age != -1) {
            person.setAge(age);
        }

        if(height != -1) {
            person.setHeight(height);
        }

        if(!cpf.trim().isEmpty()) {
            person.setCpf(cpf);
        }
        dao.updatePerson(person);
    }

    @RequestMapping("/getPersonById")
    @ExceptionHandler
    public Person getPersonById(
            @RequestParam(value="id", defaultValue="-1") long id) throws
            PersonException {
        if(id == -1) {
            throw new PersonException("Invalid ID");
        }
        return dao.getPersonById(id);
    }

    @RequestMapping("/getPerson")
    public List<Person> getPerson(
            @RequestParam(defaultValue="-1") long id,
            @RequestParam(defaultValue="") String name,
            @RequestParam(defaultValue="true") boolean exact,
            @RequestParam(defaultValue="-1") int age,
            @RequestParam(defaultValue="-1") double height,
            @RequestParam(defaultValue="-1") int olderThan,
            @RequestParam(defaultValue="-1") int youngerThan,
            @RequestParam(defaultValue="-1") double tallerThan,
            @RequestParam(defaultValue="-1") double smallerThan,
            @RequestParam(defaultValue="") String cpf) {
        if(id != -1) {
            List<Person> list = new ArrayList<>();
            list.add(dao.getPersonById(id));
        }

        if(!name.trim().isEmpty()) {
            if(exact) {
                dao.getPersonByName(name);
            } else {
                dao.getPerson(name);
            }
        }

        if(age != -1) {
            dao.getPerson(age);
        }

        if(height != -1) {
            dao.getPerson(height);
        }

        if(tallerThan != -1) {
            dao.getPersonTallerThan(tallerThan);
        }

        if(smallerThan != -1) {
            dao.getPersonSmallerThan(smallerThan);
        }

        if(olderThan != -1) {
            dao.getPersonOlderThan(olderThan);
        }

        if(youngerThan != -1) {
            dao.getPersonYougerThan(youngerThan);
        }

        if(!cpf.trim().isEmpty()) {
            dao.getPersonByCpf(cpf);
        }
        return dao.commitQuery();
    }
}
