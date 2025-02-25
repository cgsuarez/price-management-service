package com.hiberus.hiring.utils;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;

import com.google.gson.Gson;
import com.hiberus.hiring.controller.dto.OfferByPartNumberDTO;
import com.hiberus.hiring.controller.dto.OfferDTO;
import com.hiberus.hiring.entity.Offer;

public class MockDataUtils {

    private static final String MOCK_START_DATE = "2020-06-14T00.00.00Z";
    private static final String MOCK_END_DATE = "2020-06-14T23.59.59Z";

    public static Offer buildValidOffer() {
        return Offer.builder()
                .offerId(1l)
                .brandId(1l)
                .startDate(MOCK_START_DATE)
                .endDate(MOCK_END_DATE)
                .currencyIso("EUR")
                .productPartNumber("1001")
                .price(BigDecimal.valueOf(23.42))
                .priceListId(1l)
                .priority(0)
                .build();
    }

    public static Offer buildInvalidOffer() {
        return Offer.builder()
                .offerId(1l)
                .brandId(1l)
                .startDate("incorrectstartDate")
                .endDate("incorrectEndDate")
                .currencyIso("EUR")
                .productPartNumber("1001")
                .price(BigDecimal.valueOf(23.42))
                .priceListId(1l)
                .priority(0)
                .build();
    }

    public static List<OfferDTO> getListOffersDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return Arrays.asList(buildValidOffer(), buildValidOffer()).stream()
                .map(item -> modelMapper.map(item, OfferDTO.class)).toList();
    }
    
    public static List<Offer> getListOffers() {        
        return Arrays.asList(buildValidOffer(), buildValidOffer());
    }

    public static List<Offer> buildListOffersWithValidTimeLines() {        
        String filename = "offferData.jline";
        List<Offer> offers = new ArrayList<>();
        Gson gson = new Gson();
        ClassPathResource jsonResource = new ClassPathResource("testcases/" + filename);
        try (InputStream inputStream = jsonResource.getInputStream()) {
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(toList())
                    .forEach(jsonString -> offers.add(gson.fromJson(jsonString, Offer.class)));
        } catch (IOException ex) {
            throw new Error(ex.toString());
        }

        return offers;

    }

    public static List<OfferByPartNumberDTO> buildExpectedOfferByPartNumberDTO() {
        String filename = "expectedResults.jline";
        List<OfferByPartNumberDTO> offers = new ArrayList<>();
        Gson gson = new Gson();
        ClassPathResource jsonResource = new ClassPathResource("testcases/" + filename);
        try (InputStream inputStream = jsonResource.getInputStream()) {
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(toList())
                    .forEach(jsonString -> offers.add(gson.fromJson(jsonString, OfferByPartNumberDTO.class)));
        } catch (IOException ex) {
            throw new Error(ex.toString());
        }

        return offers;
    }
    

    public static boolean validateExpectedOfferByDto(List<OfferByPartNumberDTO> resultingOffersByDTO, 
            List<OfferByPartNumberDTO> expectedOffersByDTO) {
        boolean ret = false;
        if (resultingOffersByDTO.size() != expectedOffersByDTO.size()) {
            return ret;
        }
        for (int i = 0; i < resultingOffersByDTO.size(); i++) {
            boolean innerRet = true;
            OfferByPartNumberDTO resultingOfferDTO = resultingOffersByDTO.get(i);
            OfferByPartNumberDTO expectedOfferDTO = expectedOffersByDTO.get(i);
            if (!resultingOfferDTO.getCurrencyIso().equals(expectedOfferDTO.getCurrencyIso())) {
                innerRet = false;
            }
            

            if (resultingOfferDTO.getPrice().compareTo(expectedOfferDTO.getPrice())!=0) {
                innerRet = false;
            }
            if (!resultingOfferDTO.getStartDate().equals(expectedOfferDTO.getStartDate())) {
                innerRet = false;
            }
            if (!resultingOfferDTO.getEndDate().equals(expectedOfferDTO.getEndDate())) {
                innerRet = false;
            }
            if (!innerRet) {
                ret = false;
                break;
            }
            
            if (i + 1 == resultingOffersByDTO.size()) {
                ret = true;
            }
            
        }
        /*if (resultingOfferByDTO.equals(expectedOfferByDTO)) {
            ret = true;
        }*/
        
        return ret;
    }
}
