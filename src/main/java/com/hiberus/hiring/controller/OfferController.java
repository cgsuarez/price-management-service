package com.hiberus.hiring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hiberus.hiring.controller.dto.OfferByPartNumberDTO;
import com.hiberus.hiring.controller.dto.OfferDTO;
import com.hiberus.hiring.controller.dto.RestApiResponseDTO;
import com.hiberus.hiring.service.OfferService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


/**
 * You can change this controller but please do not change ends points signatures & payloads.
 */
@RestController
@RequestMapping("/offer")
@AllArgsConstructor
public class OfferController {

  private OfferService offerService;

  //Chages: Replace for specific mapping for clarity
  @Operation(summary = "Create a new offer into the server")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "The offer was created",
          content = {
              @Content(mediaType = "application/json"                      
          )})
  })
  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)    
  public void createNewOffer(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Offer with the information to be created", required = true          
      )  
    @RequestBody @Valid OfferDTO offer) {
    offerService.createNewOffer(offer);  
  }

  //Changes: Replace for specific mapping for clarity
  @Operation(summary = "Remove all the Offers from the server")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The offers was removed from the server")
  })
  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void deleteAllOffers() {
    offerService.deleteAllOffers();    
  }

  @Operation(summary = "Remove the Offer by a offerId")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The offer was removed from the server"),
      @ApiResponse(responseCode = "404", description = "The offer doesn't exists on the server",
      content={@Content(mediaType="application/json",schema=@Schema(implementation=RestApiResponseDTO.class),
                examples = @ExampleObject(value = """
                      { \"code\": \"FAIL\", \"error\": \"Error Message\"  }
                    """))})
  })
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteOfferById(@PathVariable Long id) {
    offerService.deleteOffer(id);  
  }

  @Operation(summary = "List all the Offers from the server")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The offers from the server was sended",
      content={@Content(mediaType="application/json",schema=@Schema(implementation=RestApiResponseDTO.class),
          examples = @ExampleObject(value = """
                  [{ \"offerId\": \"1234\", \"brandId\": \"1234\", 
                      \"startDate\": \"2020-06-14T00:00:00\", \"endDate\": \"2020-06-14T00:00:00\", \"priceListId\": 1,
                       \"productPartNumber\": "1002\", 
                        \"priority\": 1, \"price\": 123.2, \"currencyIso\": \"EU\" }]
              """))})
  })
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<OfferDTO> getAllOffers() {
    return offerService.getAllOffers();
  }

  @Operation(summary = "Get an offer from the id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The offer from the id was send from the server",
      content={@Content(mediaType="application/json",schema=@Schema(implementation=RestApiResponseDTO.class),
          examples = @ExampleObject(value = """
              { \"offerId\": \"1234\", \"brandId\": \"1234\", 
                      \"startDate\": \"2020-06-14T00:00:00\", \"endDate\": \"2020-06-14T00:00:00\", \"priceListId\": 1,
                       \"productPartNumber\": "1002\", 
                        \"priority\": 1, \"price\": 123.2, \"currencyIso\": \"EU\" } 
              """))}),
      @ApiResponse(responseCode = "404", description = "The offer doesn't exists on the server",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = RestApiResponseDTO.class),
                examples = @ExampleObject(value = """
                      { \"code\": \"FAIL\", \"error\": \"Error Message\"  }
                    """))})
  })
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public OfferDTO getOfferById(@PathVariable Long id) {
    return offerService.getOfferById(id);
  }

  @Operation(summary = "Get a flatten timeline of the offers with the price from the brandId and partNumber")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The flatten timeline was sended to the server",
      content={@Content(mediaType="application/json",schema=@Schema(implementation=RestApiResponseDTO.class),
              examples = @ExampleObject(value = """
                  [
                    {"startDate":"2020-06-15T11:00:00Z","endDate":"2020-06-15T15:59:59Z","price":35.50,"currencyIso":"EUR"},
                    {"startDate":"2020-06-15T16:00:00Z","endDate":"2020-12-31T23:59:59Z","price":38.95,"currencyIso":"EUR"}]
                    """)) })
  })          
  @GetMapping("/brand/{brandId}/partnumber/{partnumber}/offer") 
  @ResponseStatus(HttpStatus.OK)
  public List<OfferByPartNumberDTO> getOfferByPartNumber(@PathVariable Long brandId, @PathVariable String partnumber) {
    return  offerService.getOfferByPartNumber(brandId, partnumber);    
  }
}
