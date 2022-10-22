package mk.ukim.finki.wpaud.bootstrap;

import mk.ukim.finki.wpaud.model.Category;
import mk.ukim.finki.wpaud.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Category> categoryList = new ArrayList<>();
    public static List<User> users = new ArrayList<>();


    @PostConstruct
    public void init() {
        this.categoryList.add(new Category("Software", "Software Category"));
        this.categoryList.add(new Category("Books", "Books Category"));

        users.add(new User("dimitrija.timeski", "dt", "Dimitrija", "Timeski"));
        users.add(new User("tomislav.stojceski", "ts", "Tomislav", "Stojceski"));
        users.add(new User("branka.tasovska.stojceska", "bts", "Branka", "Tasovska"));
    }
}
