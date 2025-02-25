package com.hiberus.hiring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.hiberus.hiring.controller.dto.OfferByPartNumberDTO;
import com.hiberus.hiring.controller.dto.OfferDTO;
import com.hiberus.hiring.entity.Offer;
import com.hiberus.hiring.exceptions.OfferNotFoundException;
import com.hiberus.hiring.repository.OfferRepository;
import com.hiberus.hiring.utils.MockDataUtils;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {
    

    @InjectMocks
    private OfferService offerService;

    @MockBean
    private OfferRepository offerRepository;
    
    private ModelMapper modelMapper = new ModelMapper();

    @Before
    public void setup() {
        ReflectionTestUtils.setField(offerService, "modelMapper", modelMapper);
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void givenAValidOfferThenCreateIt() throws Exception {
        Offer offer = MockDataUtils.buildValidOffer();
        when(offerRepository.save(any())).thenReturn(offer);
        OfferDTO offerDTO = offerService
                .createNewOffer(modelMapper.map(offer, OfferDTO.class));
        assertNotNull(offerDTO);
        assertEquals(offerDTO.getOfferId(), offer.getOfferId());
    }

    @Test
    public void givenAnyOfferThenDeleteAllOffer() throws Exception {
        doNothing().when(offerRepository).deleteAll();
        offerService.deleteAllOffers();
        verify(offerRepository, times(1)).deleteAll();
    }
    
    @Test
    public void givenAnIdDeleteOfferById() throws Exception {
        doNothing().when(offerRepository).deleteById(anyLong());
        when(offerRepository.findById(anyLong()))
                .thenReturn(Optional.of(MockDataUtils.buildValidOffer()));
        offerService.deleteOffer(1l);
        verify(offerRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void givenAnIncorrectIdWhenDeleteOfferThenThrowException() throws Exception {
        doNothing().when(offerRepository).deleteById(anyLong());
        when(offerRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(OfferNotFoundException.class, () -> offerService.deleteOffer(1l));
    }
    
    @Test
    public void givenAnyWhenGetOffersThenReturnAValidList() throws Exception {
        when(offerRepository.findAll()).thenReturn(MockDataUtils.getListOffers());
        List<OfferDTO> offers = offerService.getAllOffers();
        assertEquals(2, offers.size());
    }


    @Test
    public void givenAValidIdWhenGetOfferByIdReturnAnOffer() throws Exception {
        Offer offer = MockDataUtils.buildValidOffer();
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer));
        OfferDTO offerDTO = offerService.getOfferById(1l);
        assertEquals(offerDTO.getOfferId(), offer.getOfferId());
    }
    
    @Test
    public void givenABrandIdAndPartNumberWhenCallPartsByIdThenReturnOffersByOart() throws Exception {
        List<Offer> offers = MockDataUtils.buildListOffersWithValidTimeLines();
        when(offerRepository.findByBrandIdAndProductPartNumber(anyLong(), anyString())).thenReturn(offers);
        List<OfferByPartNumberDTO> offerByPartNumberDTOs = offerService.getOfferByPartNumber(1l, "");
        List<OfferByPartNumberDTO> expecPartNumberDTOs = MockDataUtils.buildExpectedOfferByPartNumberDTO();
        assertEquals(true, MockDataUtils.validateExpectedOfferByDto(offerByPartNumberDTOs, expecPartNumberDTOs));
    }

    
}
