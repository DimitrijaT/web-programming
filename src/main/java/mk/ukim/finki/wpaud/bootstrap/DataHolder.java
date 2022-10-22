package mk.ukim.finki.wpaud.bootstrap;

import mk.ukim.finki.wpaud.model.Category;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Category> categoryList = new ArrayList<>();
    @PostConstruct
    public void init() {
        this.categoryList.add(new Category("Software", "Software Category"));
        this.categoryList.add(new Category("Books", "Books Category"));
    }
}
