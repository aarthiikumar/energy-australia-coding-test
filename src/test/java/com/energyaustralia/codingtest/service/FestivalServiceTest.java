package com.energyaustralia.codingtest.service;

import com.energyaustralia.codingtest.model.Band;
import com.energyaustralia.codingtest.model.Festival;
import com.energyaustralia.codingtest.model.RecordLabel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestPropertySource(properties = {"api.url=https://mock/festivals"})
public class FestivalServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private FestivalService service;

    private static final Logger logger = LoggerFactory.getLogger(FestivalServiceTest.class);

    private Band band;
    private List<Festival> expectedFestivals;

    @BeforeEach
    void setUp() {

        Festival festival = createFestival();
        expectedFestivals = Collections.singletonList(festival);
        ResponseEntity<List<Festival>> mockResponse = new ResponseEntity<>(expectedFestivals, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://mock/festivals"),
                eq(HttpMethod.GET),
                eq(null),
                eq(new ParameterizedTypeReference<List<Festival>>() {})))
                .thenReturn(mockResponse);
    }

    private Festival createFestival() {
        Festival festival = new Festival();
        festival.setName("Festival1");
        band = new Band();
        band.setName("Band1");
        band.setRecordLabel("Record1");
        festival.setBands(Collections.singletonList(band));
        return festival;
    }

    @Test
    void testMapFestivalsToRecordLabels() {
        List<RecordLabel> result = service.mapFestivalsToRecordLabels(expectedFestivals);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(band.getRecordLabel(), result.get(0).getLabel());
    }

    @Test
    public void testFetchFestivals() {
        List<Festival> actualFestivals = service.fetchFestivals();
        assertEquals(expectedFestivals, actualFestivals);
    }
}