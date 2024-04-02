package com.energyaustralia.codingtest.service;

import com.energyaustralia.codingtest.exception.CustomException;
import com.energyaustralia.codingtest.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import org.springframework.core.ParameterizedTypeReference;


/**
 * The FestivalService class is a service class that is responsible for fetching festivals from an API endpoint
 * and mapping the festivals to record labels.
 */
@Service
public class FestivalService {
    private static final Logger logger = LoggerFactory.getLogger(FestivalService.class);

    @Value("${api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public FestivalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches the festivals from the API endpoint and returns a list of Festival objects.
     * The API endpoint is defined by the 'apiUrl' field.
     *
     * @return a list of Festival objects
     * @throws CustomException if there is an error fetching the festivals from the API
     */
    public List<Festival> fetchFestivals() {
        try {
            logger.info("Fetching Festivals from API endpoint: {}", apiUrl);
            // https://eacp.energyaustralia.com.au/codingtest/api-docs/#/festivals/APIFestivalsGet
            // The code below makes a GET request to the API endpoint at 'apiUrl'
            // (https://eacp.energyaustralia.com.au/codingtest/api/v1/festivals),
            // expecting a list of Festival objects in return. The response body is extracted and stored in the 'festivals' variable.
            List<Festival> festivals = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Festival>>() {}
            ).getBody();

            logger.info("Fetched {} festivals", (festivals != null ? festivals.size() : 0));
            return festivals;
        } catch (RestClientException e) {
            logger.error("RestClientException occurred while fetching festivals: {}", e.getMessage());
            throw new CustomException("Error: Unable to retrieve data from server. " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching festivals: {}", e.getMessage());
            throw new CustomException("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Maps festivals to record labels.
     *
     * @param festivals the list of festivals to be mapped
     * @return a list of record labels
     */
    public List<RecordLabel> mapFestivalsToRecordLabels(List<Festival> festivals) {
        logger.info("Mapping {} festivals to record labels", festivals.size());

        Map<String, Map<String, List<String>>> map = new HashMap<>();

        for(Festival festival : festivals){
            for(Band band : festival.getBands()){
                map.computeIfAbsent(band.getRecordLabel(), k -> new HashMap<>())
                        .computeIfAbsent(band.getName(), k -> new ArrayList<>())
                        .add(festival.getName());
            }
        }
        logger.debug("Map constructed: {}", map);

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
        logger.info("Mapped festivals to {} record labels", recordLabels.size());
        return recordLabels;
    }
}