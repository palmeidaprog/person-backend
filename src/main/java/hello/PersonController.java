package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    private PersonDao dao;

    public PersonController() {
        dao = PersonDaoFactory.getInstance();
    }

    @RequestMapping("/getPerson")
    public Person getPerson(
            @RequestParam(value="name", defaultValue="") String name,
            @RequestParam(value="age", defaultValue="-1") int age,
            @RequestParam(value="height", defaultValue="-1") double height) {
        return new Person("teste", 10, 10.1);
    }

    @RequestMapping("/addPerson")
    public void addPerson (
            @RequestParam(value="name") String name,
            @RequestParam(value="age", defaultValue="-1") int age,
            @RequestParam(value="height", defaultValue="-1") double height,
            @RequestParam(value="cpf", defaultValue="") String cpf) throws
            Exception {

        if(name.trim().isEmpty()) {
            throw new Exception("Can't create a person without a name");
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
    public void removePerson(@RequestParam(value="id") long id) {
        dao.removePerson(id);
    }


}
