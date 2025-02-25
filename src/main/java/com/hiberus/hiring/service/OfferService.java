package com.hiberus.hiring.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hiberus.hiring.controller.dto.OfferByPartNumberDTO;
import com.hiberus.hiring.controller.dto.OfferDTO;
import com.hiberus.hiring.entity.Offer;
import com.hiberus.hiring.exceptions.OfferNotFoundException;
import com.hiberus.hiring.repository.OfferRepository;
import com.hiberus.hiring.utils.DateTimeUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * The {@code OfferService} class provides a backend service for managing offers.
 * This service handles various operations related to offers, such as creating, retrieving, updating,
 * and deleting offers. 
 * <p>
 * This service is designed to be scalable and efficient, utilizing Spring Boot and JPA.
 * <p>
 * **Key Features:**
 * <ul>
 *     <li>Offer Management: Create, retrieve, update, and delete offers.</li>
 *     <li>Flatten: Given a Brand and a Part number return a list of prices flatten with a proper timeline 
 * </ul>
 * <p>
 * **Dependencies:**
 * <ul>
 *     <li>Spring Boot</li>
 *      <li>Spring JPA</li>
 * </ul>
 * @author Christian Suarez
 * @version 1.0.0
 * @since 24-Feb-2025
 */
@Service
@Slf4j
public class OfferService {
    
    private OfferRepository offerRepository;
    private ModelMapper modelMapper;


    public OfferService(OfferRepository offerRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Method that creates a new offer according to the parameter
     * @param offerDTO  The OfferDTO to create
     * @return  The OfferDTO created on the persistence repository
     */
    public OfferDTO createNewOffer(OfferDTO offerDTO) {

        Offer offer = modelMapper.map(offerDTO, Offer.class);
        offerRepository.save(offer);
        return modelMapper.map(offer, OfferDTO.class);

    }
    
    /**
     * Delete all the offers from the persistence repository
     */
    public void deleteAllOffers() {
        offerRepository.deleteAll();        
    }


    /**
     * Delete the offer by an Id
     * @param offerId   The offer id
     */
    public void deleteOffer(Long offerId) {
        findOfferById(offerId);        
        offerRepository.deleteById(offerId);
    }

    /**
     * Get all the offers on the persistence layer
     * @return  The list of offers
     */
    public List<OfferDTO> getAllOffers() {
        return offerRepository
                .findAll().stream()
                .map(item -> modelMapper.map(item, OfferDTO.class))
                .collect(Collectors.toList());
    }
    
    /**
     * Get the offer by an identifier
     * @param offerId   The Identifier 
     * @return  The offer found
     */
    public OfferDTO getOfferById(Long offerId) {        
        return modelMapper.map(findOfferById(offerId), OfferDTO.class);
    }

    /**
     * Util method that handle the operation of find the offer
     * or throws an exception in case of missing the offer on
     * the persistence layer
     * @param id    The offer id  
     * @return  The offer found on the persistence layer
     */
    private Offer findOfferById(Long id) {
        Optional<Offer> optOffer = offerRepository.findById(id);
        if (optOffer.isEmpty()) {            
            throw new OfferNotFoundException("Offer Not found");
        }
        return optOffer.get();
    }

    /**
     * Method that Flatten the offers and create a timeline.
     * @param brandId   The brand id of the offer to search
     * @param partNumber    The part number of the offer to search
     * @return  The list of offers by parts
     */
    public List<OfferByPartNumberDTO> getOfferByPartNumber(Long brandId, String partNumber) {
        return DateTimeUtils
                .buildFlatternOffer(
                        offerRepository.findByBrandIdAndProductPartNumber(brandId, partNumber)
                    );
    }
    

}
