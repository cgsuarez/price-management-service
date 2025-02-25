package com.hiberus.hiring.controller.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*
 * Use this POJO on the reponse for brand & partnumber & offer endPoint.
 * 
 *******************************
 * - The original class name was modified by adding a DTO to reflect 
 *   the pattern explicitly.
 * - Added Lombok for simplicity
 * *******************************
 */

 /**
 * DTO which represents the offer by part number
 * @author Christian Suarez
 * @version 1.0.0
 * @since 24-Feb-2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class OfferByPartNumberDTO implements Serializable {

  private String startDate;
  private String endDate;
  private BigDecimal price;
  private String currencyIso;


}