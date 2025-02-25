package com.hiberus.hiring.controller.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
 * Use this POJO for offer service end point responses.
 * 
 *******************************
 * - The original class name was modified by adding a DTO to reflect 
 *   the pattern explicitly.
 * - Added Lombok for simplicity
 * *******************************
 */

 /**
 * Use this POJO for offer service end point responses.
 * @author Christian Suarez
 * @version 1.0.0
 * @since 24-Feb-2025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferDTO implements Serializable {


  private Long offerId;
  
  @NotNull(message = "Brand id cannot be blank")
  private Integer brandId;

  @NotBlank(message = "Start Date cannot be blank")
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}.\\d{2}.\\d{2}Z")
  private String startDate;

  @NotBlank(message = "End Date cannot be blank")
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}.\\d{2}.\\d{2}Z")
  private String endDate;
  private Long priceListId;
  private String productPartNumber;
  private Integer priority;

  @NotNull(message = "Price cannot be blank")
  private BigDecimal price;
  private String currencyIso;


}