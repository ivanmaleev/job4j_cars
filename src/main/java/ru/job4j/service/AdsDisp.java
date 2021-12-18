package ru.job4j.service;

import ru.job4j.model.Advertisement;
import ru.job4j.store.AdRepostiroty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class AdsDisp {
    private Map<String, Supplier<List<Advertisement>>> dispatch = new HashMap<>();

    public AdsDisp init() {
        this.load("unsold", this.unsoled());
        this.load("lastday", this.lastDay());
        this.load("withphoto", this.withPhoto());
        return this;
    }

    private Supplier<List<Advertisement>> unsoled() {
        return () -> AdRepostiroty.instOf().findAllUnsoldAds();
    }

    private Supplier<List<Advertisement>> withPhoto() {
        return () -> AdRepostiroty.instOf().findAdsWithPhoto();
    }

    private Supplier<List<Advertisement>> lastDay() {
        return () -> AdRepostiroty.instOf().findAdsLastDay();
    }

    public void load(String viewtype, Supplier<List<Advertisement>> handle) {
        this.dispatch.put(viewtype, handle);
    }

    public List<Advertisement> findAds(String viewtype) {
        return this.dispatch.get(viewtype).get();
    }
}
