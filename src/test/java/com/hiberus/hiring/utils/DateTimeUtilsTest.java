package com.hiberus.hiring.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.hiberus.hiring.controller.dto.OfferByPartNumberDTO;
import com.hiberus.hiring.entity.Offer;

public class DateTimeUtilsTest {
    
    @Test
    public void givenOffersGenerateTimeLine() throws Exception {

        List<Offer> offersToTest = MockDataUtils.buildListOffersWithValidTimeLines();
        List<OfferByPartNumberDTO> expectedOfferByParts = MockDataUtils.buildExpectedOfferByPartNumberDTO();
        List<OfferByPartNumberDTO> ret = DateTimeUtils.buildFlatternOffer(offersToTest);        
        assertEquals(true, MockDataUtils.validateExpectedOfferByDto(ret, expectedOfferByParts));
    }

    

}
