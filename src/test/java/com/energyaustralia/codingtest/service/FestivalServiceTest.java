package com.energyaustralia.codingtest.service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import com.energyaustralia.codingtest.model.Band;
import com.energyaustralia.codingtest.model.Festival;
import com.energyaustralia.codingtest.model.RecordLabel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class FestivalServiceTest {

    @InjectMocks
    private FestivalService festivalService;

    @Test
    void testMapFestivalsToRecordLabels() {
        festivalService = new FestivalService();
        Festival festival = new Festival();
        festival.setName("Festival1");
        Band band = new Band();
        band.setName("Band1");
        band.setRecordLabel("Record1");
        festival.setBands(List.of(band));

        List<RecordLabel> result = festivalService.mapFestivalsToRecordLabels(List.of(festival));
        // Check that the result is not null
        assertNotNull(result);

        // Check that the result has the correct number of elements
        assertEquals(1, result.size());

        // Check that the result contains the correct records
        assertEquals(result.get(0).getLabel(), band.getRecordLabel());
    }
}