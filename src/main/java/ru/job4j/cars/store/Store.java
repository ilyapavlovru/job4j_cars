package ru.job4j.cars.store;

import ru.job4j.cars.model.*;

import java.util.Collection;

public interface Store {

    Adv saveAdv(Adv adv);

    Adv updateAdv(Adv adv);

    Collection<Adv> findAllAds();

    Collection<Adv> findTodayAds();

    Collection<Adv> findAdsWithPhoto();

    Collection<Adv> findAdsByCarBrandId(int carBrandId);

    Collection<Adv> findAllActiveAds();

    Adv findAdvById(int id);

    Collection<Adv> findAllAdsByUserId(int id);

    Collection<CarBrand> findAllCarBrands();

    Collection<CarBodyType> findAllCarBodyTypes();

    CarBrand findCarBrandById(Integer carBrandId);

    CarBodyType findCarBodyTypeById(Integer carBodyTypeId);

    User findUserByEmail(String email);

    User findUserByPhone(String phone);

    User addUser(User user);

    Role findRoleByName(String name);

    Role addRole(Role role);
}
