package com.hiberus.hiring.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hiberus.hiring.controller.dto.OfferByPartNumberDTO;
import com.hiberus.hiring.controller.dto.OfferDTO;
import com.hiberus.hiring.entity.Offer;
import com.hiberus.hiring.exceptions.OfferNotFoundException;
import com.hiberus.hiring.handlers.OfferServiceExceptionHandler;
import com.hiberus.hiring.service.OfferService;
import com.hiberus.hiring.utils.MapperUtils;
import com.hiberus.hiring.utils.MockDataUtils;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class OfferControllerTest {
    
    private static final String ENDPOINT = "/offer";    

    @InjectMocks
    private OfferController offerController;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    
    private ModelMapper modelMapper = new ModelMapper();

    @Before
    public void setup() {

        MockitoAnnotations.openMocks(this);
        
        //ReflectionTestUtils.setField(offerService, "modelMapper", modelMapper);
        //ReflectionTestUtils.setField(offerService, "offerRepository", offerRepository);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(offerController)
                .setControllerAdvice(new OfferServiceExceptionHandler()).build();
    }

    @Test
    public void givenCorrectTimeStampThenCreateProduct() throws Exception {
        Offer offer = MockDataUtils.buildValidOffer();
        when(offerService.createNewOffer(any())).thenReturn(modelMapper.map(offer, OfferDTO.class));
        mockMvc.perform(MockMvcRequestBuilders
                .post(ENDPOINT)
                .content(MapperUtils.jsonAsString(modelMapper.map(offer, OfferDTO.class)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());                
    }


    @Test
    public void givenOffersTheDeleteAll() throws Exception {
        
        doNothing().when(offerService).deleteAllOffers();
        mockMvc.perform(MockMvcRequestBuilders
                .delete(ENDPOINT))
                .andExpect(status().isOk());
    }
    
    @Test
    public void givenCorrectOfferIdThenDelete() throws Exception {
        Offer offer = MockDataUtils.buildValidOffer();        
        when(offerService.getOfferById(any())).thenReturn(modelMapper.map(offer, OfferDTO.class));
        mockMvc.perform(MockMvcRequestBuilders
                .delete(String.format("%s/%d", ENDPOINT, offer.getOfferId())))
                .andExpect(status().isOk());

    }
    
    @Test
    public void givenInCorrectOfferIdThenDelete() throws Exception {
        Offer offer = MockDataUtils.buildValidOffer();
        doThrow(new OfferNotFoundException("Not Found")).when(offerService).deleteOffer(any());
        mockMvc.perform(MockMvcRequestBuilders
                .delete(String.format("%s/%d", ENDPOINT, offer.getOfferId())))
                .andExpect(status().isNotFound());

    }
    
    @Test
    public void givenOffersThenGetList() throws Exception {
        when(offerService.getAllOffers()).thenReturn(MockDataUtils.getListOffersDTO());
        mockMvc.perform(MockMvcRequestBuilders
                .get(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())                
                .andExpect(jsonPath("$.length()").value(2));
    }
    
    @Test
    public void givenCorrectOfferIdThenReturn() throws Exception 
    {
        Offer offer = MockDataUtils.buildValidOffer();
        when(offerService.getOfferById(any())).thenReturn(modelMapper.map(offer, OfferDTO.class));
        mockMvc.perform(MockMvcRequestBuilders
                .get(String.format("%s/%d", ENDPOINT, offer.getOfferId()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").exists());                

    }
    
    @Test
    public void givenInCorrectOfferIdThenReturnFailure() throws Exception 
    {
        Offer offer = MockDataUtils.buildValidOffer();
        doThrow(new OfferNotFoundException("Not Found")).when(offerService).getOfferById(any());
        mockMvc.perform(MockMvcRequestBuilders
                .get(String.format("%s/%d", ENDPOINT, offer.getOfferId()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
    
    @Test
    public void givenABrandIdAndPartNumberThenReturnAListOfParts() throws Exception {
        List<OfferByPartNumberDTO> offerByPartNumberDTOs = MockDataUtils.buildExpectedOfferByPartNumberDTO();
        when(offerService.getOfferByPartNumber(anyLong(), anyString())).thenReturn(offerByPartNumberDTOs);        
        mockMvc.perform(MockMvcRequestBuilders
                .get(String.format("%s/brand/%d/partnumber/%s/offer", ENDPOINT, 1l, "partid"))                
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6));
    }
                        
                            

}
