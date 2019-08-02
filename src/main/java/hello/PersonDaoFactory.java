package hello;

public class PersonDaoFactory {

    public static PersonDao getInstance() {
        return new PersonDaoDatabase();
    }
}
