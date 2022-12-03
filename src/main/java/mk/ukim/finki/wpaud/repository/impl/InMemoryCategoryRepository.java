package mk.ukim.finki.wpaud.repository.impl;

import mk.ukim.finki.wpaud.bootstrap.DataHolder;
import mk.ukim.finki.wpaud.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryCategoryRepository {

    //Листање на сите категории
    public List<Category> findAll() {
        return DataHolder.categories;
    }

    //Зачувување на категорија (пребриши ако постои истото име)
    public Category save(Category c) {
        if (c == null || c.getName() == null || c.getName().isEmpty()) {
            return null;
        }
        DataHolder.categories.removeIf(r -> r.getName().equals(c.getName())); //izbrishi go stariot
        DataHolder.categories.add(c);
        return c;
    }

    //Пребарување на категорија по име
    public Optional<Category> findByName(String name) {
        return DataHolder.categories.stream().filter(r -> r.getName().equals(name)).findFirst();
    }

    public Optional<Category> findById(Long id) {
        return DataHolder.categories.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    //Пребарување на matching текст во листата на категории (и во името и во описот)
    public List<Category> search(String text) {
        return DataHolder.categories.stream().filter(r -> r.getName().contains(text) || r.getDescription().contains(text)).collect(Collectors.toList());
    }

    //Избриши категорија по име
    public void delete (String name){
        if (name == null){
            return;
        }
         DataHolder.categories.removeIf(r -> r.getName().equals(name));
    }
}
