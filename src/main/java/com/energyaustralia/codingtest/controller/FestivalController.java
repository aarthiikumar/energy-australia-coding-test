package com.energyaustralia.codingtest.controller;

import com.energyaustralia.codingtest.exception.CustomException;
import com.energyaustralia.codingtest.model.*;
import com.energyaustralia.codingtest.service.FestivalService;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
public class FestivalController {

    private final FestivalService festivalService;

    @Autowired
    public FestivalController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    // Returns payload for music record labels
    @GetMapping("/musicRecords")
    public List<RecordLabel> getSortedRecordLabelsFromFestivals() {
        List<Festival> festivals = festivalService.fetchFestivals();

        if(festivals == null) {
            throw new CustomException("Error: Unable to retrieve data from server.");
        }

        List<RecordLabel> recordLabels = festivalService.mapFestivalsToRecordLabels(festivals);
        recordLabels.sort(Comparator.comparing(RecordLabel::getLabel, Comparator.nullsLast(Comparator.naturalOrder())));
        return recordLabels;
    }
}