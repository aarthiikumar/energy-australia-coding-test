package com.energyaustralia.codingtest.model;

import java.util.List;

public class RecordLabel {
    private String label;
    private List<BandFestivals> bands;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<BandFestivals> getBands() {
        return bands;
    }

    public void setBands(List<BandFestivals> bands) {
        this.bands = bands;
    }
}
