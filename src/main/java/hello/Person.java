package hello;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="person")
public class Person {
    @Id
    @GeneratedValue
    private long id;

    private int age;
    private String name;
    private double height;
    private String cpf;

    public Person() {

    }

    public Person(String name) {
        this.name = name;
    }

    public Person(long id) {
        this.id = id;
    }

    public Person(String name, int age) {
        this(name);
        this.age = age;
    }

    public Person(String name, double height) {
        this(name);
        this.height = height;
    }

    public Person(String name, int age, double height) {
        this(name, age);
        this.height = height;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", height=" + height +
                '}';
    }
}
