package ru.job4j.cars.store;

import ru.job4j.cars.model.Adv;
import ru.job4j.cars.model.Role;
import ru.job4j.cars.model.User;

import java.util.Collection;

public interface Store {
    Collection<Adv> findAllAds();
//    Collection<Category> findAllCategories();
//    Item addItem(Item item, String[] ids);
    Adv findAdvById(int id);
//    boolean replaceItem(Item item);
    User findUserByEmail(String email);
    User addUser(User user);
    Role findRoleByName(String name);
    Role addRole(Role role);
}
