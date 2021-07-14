package ru.job4j.cars.store;

import ru.job4j.cars.model.Adv;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.model.Role;
import ru.job4j.cars.model.User;

import java.util.Collection;

public interface Store {

    Collection<Adv> findAllAds();
    Collection<Adv> findTodayAds();
    Collection<Adv> findAdsWithPhoto();
    Collection<Adv> findAdsByCarBrandId(int carBrandId);
    Adv findAdvById(int id);

    Collection<CarBrand> findAllCarBrands();

//    Collection<Category> findAllCategories();
    User findUserByEmail(String email);

    User addUser(User user);
    Role findRoleByName(String name);

    Role addRole(Role role);
}
