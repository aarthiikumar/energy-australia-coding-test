package com.energyaustralia.codingtest.model;

import java.util.List;

public class BandFestivals {
    private String name;
    private List<FestivalName> festivals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FestivalName> getFestivals() {
        return festivals;
    }

    public void setFestivals(List<FestivalName> festivals) {
        this.festivals = festivals;
    }
}