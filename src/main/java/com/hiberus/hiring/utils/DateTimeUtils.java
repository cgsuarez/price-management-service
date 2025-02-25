package com.hiberus.hiring.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hiberus.hiring.controller.dto.OfferByPartNumberDTO;
import com.hiberus.hiring.entity.Offer;

/**
 * Utlitary class that handle the flatten process
 * @author Christian Suarez
 * @version 1.0.0
 * @since 24-Feb-2025
 */
public class DateTimeUtils {

    /**
     * Given a list of offers this method flatten the orders and generate
     * a timeline according to the required rules.
     * @param offers    List of offers
     * @return  List of offers by parts which represents a timeline with the prices
     */
    public static List<OfferByPartNumberDTO> buildFlatternOffer(List<Offer> offers) {

        //First sort the list according to the start date and then by the end date
        List<OfferByPartNumberDTO> flattenedItems = new ArrayList<>();
        Collections.sort(offers, Comparator
                .comparing(Offer::getStartDateAsObject)
                .thenComparing((Offer item) -> item.getEndDateAsObject()));
        
        LocalDateTime earliersDateTime = null;
        LocalDateTime latestDateTime = null;
        Long currentOfferId = null;
        int index = 0;
        
        //Iterate over the offers
        for (Offer offer : offers) {
            //Only we need to substract the second for the end date 
            //for all the offers except the last one
            int secondsToSubstract = (index + 1) == offers.size() ? 0 : 1;
            //If is the first element store on the return list without any calculation
            if(earliersDateTime==null && latestDateTime == null){
                earliersDateTime = offer.getStartDateAsObject();
                latestDateTime = offer.getEndDateAsObject();
                currentOfferId = offer.getOfferId();

                flattenedItems.add(getOfferByPartNumber(offer, offer.getStartDate(),
                                 offer.getEndDate()));
                
            } else if(offer.getStartDateAsObject().isBefore(latestDateTime) 
                    || offer.getStartDateAsObject().equals(latestDateTime)) {
                    
                    //if the current offer has an overlaps the previous one
                    //alter the end date for the last with the next start date minus 1 sec
                    OfferByPartNumberDTO lastOffer = flattenedItems.get(flattenedItems.size() - 1);
                    lastOffer.setEndDate(convertDateToISO8601(offer.getStartDateAsObject().minusSeconds(1)));
                    flattenedItems.set(flattenedItems.size() - 1, lastOffer);
                    earliersDateTime = offer.getStartDateAsObject();
                    latestDateTime = offer.getEndDateAsObject();
                    currentOfferId = offer.getOfferId();


                     flattenedItems.add(getOfferByPartNumber(offer, offer.getStartDate(),
                                        convertDateToISO8601(offer.getEndDateAsObject().minusSeconds(secondsToSubstract))));


                    
            } else {
                    
                //Otherwise check if we have an overlap with all the offers of the list
                Offer currentOfferOverlap = null;                    
                for (Offer offerInner : offers) {                        
                    if (checkIFOfferOverlaps(latestDateTime, offerInner, currentOfferId)) {                                
                            //We need to traverse all the list in order to check if we 
                            //have other offer with hightes priority
                            if(currentOfferOverlap==null){
                                currentOfferOverlap = offerInner;                                    
                            }else if(currentOfferOverlap.getPriority()<offerInner.getPriority()){
                                currentOfferOverlap = offerInner;
                            }                                                                                                
                        } 
                }

                if(currentOfferOverlap!=null){
                    flattenedItems.add(getOfferByPartNumber(currentOfferOverlap, convertDateToISO8601(latestDateTime),
                            convertDateToISO8601(offer.getStartDateAsObject().minusSeconds(1))));
                            
                }
                
                //Stores the offer taking in consideration the overlap
                earliersDateTime = offer.getStartDateAsObject();
                latestDateTime = offer.getEndDateAsObject();
                currentOfferId = offer.getOfferId();
                flattenedItems.add(getOfferByPartNumber(offer, offer.getStartDate(),
                        convertDateToISO8601(offer.getEndDateAsObject().minusSeconds(secondsToSubstract))));
                    
             }
           

            index++;
            
        }        
        return flattenedItems;
    }
    

    /**
     * Method that checks if the currentDatTime overlapps an offer
     * @param currentDateTime   The current date time
     * @param offerInner    The offer inner
     * @param currentOfferId    The offer which is discarted by the analysis
     * @return  true if the offer overlaps the timeline or false otherwise
     */
    private static boolean checkIFOfferOverlaps(LocalDateTime currentDateTime, Offer offerInner, Long currentOfferId) {
        return offerInner.getOfferId() != currentOfferId &&
            (offerInner.getStartDateAsObject().isBefore(currentDateTime) ||
            offerInner.getStartDateAsObject().equals(currentDateTime)) &&
            (offerInner.getEndDateAsObject().isAfter(currentDateTime) ||
                offerInner.getEndDateAsObject().equals(currentDateTime));
                                
    }

    /**
     * Method that get the offer by a part number and build the object
     * @param offer The offer to be built
     * @param startDate The sart date of the part item
     * @param endDate   The end date of the part item
     * @return  The object which represents the item by part number to be stored
     */
    private static OfferByPartNumberDTO getOfferByPartNumber(Offer offer, String startDate, String endDate) {
        return OfferByPartNumberDTO.builder()
                .price(offer.getPrice())
                .currencyIso(offer.getCurrencyIso())
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
    
    /**
     * Method which converts the date from Local Date Time to ISO8601
     * @param currentDate   The current date
     * @return  The string represents the date on the ISO8601 format
     */
    private static String convertDateToISO8601(LocalDateTime currentDate) {
        return currentDate.format(DateTimeFormatter.ISO_DATE_TIME) + "Z";
    }

    
}
