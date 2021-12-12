package ru.job4j.store;

import ru.job4j.model.Advertisement;
import ru.job4j.model.CarBrand;
import ru.job4j.model.User;

import java.util.List;

public interface Store {

    List<Advertisement> findAllUnsoldAds();

    List<Advertisement> findAllAdsByUserId(int userid);

    List<Advertisement> findAdsLastDay();

    List<Advertisement> findAdsWithPhoto();

    List<Advertisement> findAdsByBrand(CarBrand brand);

    CarBrand findCarBrandByName(String brand);

    CarBrand saveCarBrand(CarBrand carBrand);

    Advertisement saveAd(Advertisement advertisement);

    Advertisement findAdById(int id);

    User saveUser(User user);

    User findUserByEmail(String email);

    User findUserById(int id);
}
