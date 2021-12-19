package ru.job4j.service;

import ru.job4j.model.*;
import ru.job4j.store.AdRepostiroty;

import java.util.ArrayList;
import java.util.List;

public class AdsService {
    private AdsService() {
    }

    public static AdsService instOf() {
        return Lazy.INSTANCE;
    }

    private static final class Lazy {
        private static final AdsService INSTANCE = new AdsService();
    }

    public List<Advertisement> findAds(String viewtype) {
        AdsDisp adsDisp = new AdsDisp();
        adsDisp.init();
        return adsDisp.findAds(viewtype);
    }

    public List<Advertisement> findAds(int userid) {
        return AdRepostiroty.instOf().findAllAdsByUserId(userid);
    }

    public Advertisement saveAd(User user, String id,
                                String description, String carBrandStr,
                                String bodyTypeStr, String fileName) {
        Advertisement ad = AdRepostiroty.instOf().findAdById(Integer.parseInt(id));
        if (ad != null) {
            ad.setDescription(description);
            Car car = ad.getCar();
            CarBrand brand = car.getBrand();
            if (!brand.getName().equals(carBrandStr)) {
                CarBrand newCarBrand = new CarBrand();
                newCarBrand.setName(carBrandStr);
                newCarBrand = AdRepostiroty.instOf().saveCarBrand(newCarBrand);
            }
            car.setBodytype(BodyType.valueOf(bodyTypeStr));
            car.setPhotoPath(fileName);
            ad = AdRepostiroty.instOf().saveAd(ad);
        } else {
            Car car = new Car();
            CarBrand brand = AdRepostiroty.instOf().findCarBrandByName(carBrandStr);
            if (brand == null) {
                brand = new CarBrand();
                brand.setName(carBrandStr);
                brand = AdRepostiroty.instOf().saveCarBrand(brand);
            }
            car.setBodytype(BodyType.valueOf(bodyTypeStr));
            car.setBrand(brand);
            car.setPhotoPath(fileName);
            ad = new Advertisement(description, car, user);
            ad = AdRepostiroty.instOf().saveAd(ad);
        }
        return ad;
    }

    public Advertisement setSoled(String id) {
        Advertisement ad = AdRepostiroty.instOf().findAdById(Integer.parseInt(id));
        ad.setSold(!ad.isSold());
        return AdRepostiroty.instOf().saveAd(ad);
    }

}
