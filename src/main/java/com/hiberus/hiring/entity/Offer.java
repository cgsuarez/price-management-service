package com.hiberus.hiring.entity;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represent the offer to be stored on the database
 * @author Christian Suarez
 * @version 1.0.0
 * @since 24-Feb-2025
 */
@Entity
@Table(name = "OFFER")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Offer {
    
    @Id
    private Long offerId;
    private Long brandId;
    private String startDate;
    private String endDate;
    private Long priceListId;
    private String productPartNumber;
    private Integer priority;
    private BigDecimal price;
    private String currencyIso;

    @Transient
    public LocalDateTime getStartDateAsObject() {
        return convertISO8601(this.startDate);
    }

    @Transient
    public LocalDateTime getEndDateAsObject() {
        return convertISO8601(this.endDate);
    }


    private static LocalDateTime convertISO8601(String dateStr) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
    }
}
