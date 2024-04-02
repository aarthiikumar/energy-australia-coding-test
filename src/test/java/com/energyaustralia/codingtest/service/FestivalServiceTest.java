package com.energyaustralia.codingtest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import com.energyaustralia.codingtest.model.Band;
import com.energyaustralia.codingtest.model.Festival;
import com.energyaustralia.codingtest.model.RecordLabel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentMatchers;
;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.junit.runner.RunWith;



@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"api.url=https://mock/festivals"})
public class FestivalServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(FestivalServiceTest.class);

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private FestivalService service;

    @Test
    void testMapFestivalsToRecordLabels() {
        Festival festival = new Festival();
        festival.setName("Festival1");
        Band band = new Band();
        band.setName("Band1");
        band.setRecordLabel("Record1");
        festival.setBands(List.of(band));

        List<RecordLabel> result = service.mapFestivalsToRecordLabels(List.of(festival));
        // Check that the result is not null
        assertNotNull(result);

        // Check that the result has the correct number of elements
        assertEquals(1, result.size());

        // Check that the result contains the correct records
        assertEquals(result.get(0).getLabel(), band.getRecordLabel());
    }

    @Test
    public void fetchFestivals() {

        //given
        Festival festival = new Festival();
        festival.setName("Festival1");
        Band band = new Band();
        band.setName("Band1");
        band.setRecordLabel("Record1");
        festival.setBands(List.of(band));
        List<Festival> expectedFestivals = new ArrayList<>();
        expectedFestivals.add(festival);
        ResponseEntity<List<Festival>> mockResponse = new ResponseEntity<>(expectedFestivals, HttpStatus.OK);
        when(restTemplate.exchange(
                eq("https://mock/festivals"),
                eq(HttpMethod.GET),
                eq(null),
                        ArgumentMatchers.<ParameterizedTypeReference<List<Festival>>>any()))

                .thenReturn(mockResponse);
        //when
        List<Festival> actualFestivals = service.fetchFestivals();

        //then
        assertEquals(expectedFestivals, actualFestivals);
    }
}