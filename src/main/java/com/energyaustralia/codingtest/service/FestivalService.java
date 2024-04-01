package com.energyaustralia.codingtest.service;

import com.energyaustralia.codingtest.exception.CustomException;
import com.energyaustralia.codingtest.model.*;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import org.springframework.core.ParameterizedTypeReference;

@Service
public class FestivalService {

    private static final String URI = "https://eacp.energyaustralia.com.au/codingtest/api/v1/festivals";

    // Fetch list of festivals from api
    public List<Festival> fetchFestivals() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(
                    URI,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Festival>>() {
                    }
            ).getBody();
        } catch (RestClientException e) {
            throw new CustomException("Error: Unable to retrieve data from server. " + e.getMessage());
        } catch (Exception e) {
            throw new CustomException("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Prepare the final list of record labels
    public List<RecordLabel> mapFestivalsToRecordLabels(List<Festival> festivals) {
        Map<String, Map<String, List<String>>> map = new HashMap<>();

        for(Festival festival : festivals){
            for(Band band : festival.getBands()){
                map.computeIfAbsent(band.getRecordLabel(), k -> new HashMap<>())
                        .computeIfAbsent(band.getName(), k -> new ArrayList<>())
                        .add(festival.getName());
            }
        }

        List<RecordLabel> recordLabels = new ArrayList<>();
        for(Map.Entry<String, Map<String, List<String>>> mapEntry : map.entrySet()){
            RecordLabel recordLabel = new RecordLabel();
            recordLabel.setLabel(mapEntry.getKey());

            List<BandFestivals> bandFestivals = new ArrayList<>();
            for(Map.Entry<String, List<String>> bandEntry : mapEntry.getValue().entrySet()){
                BandFestivals band = new BandFestivals();
                band.setName(bandEntry.getKey());

                List<FestivalName> festivalsList = new ArrayList<>();
                for(String festival : bandEntry.getValue()) {
                    festivalsList.add(new FestivalName(festival));
                }

                band.setFestivals(festivalsList);
                bandFestivals.add(band);
            }

            recordLabel.setBands(bandFestivals);
            recordLabels.add(recordLabel);
        }

        return recordLabels;
    }
}