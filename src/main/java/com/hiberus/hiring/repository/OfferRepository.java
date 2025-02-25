package com.hiberus.hiring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hiberus.hiring.entity.Offer;

/**
 * Interface with the Offer CRUD operations.
 * @author Christian Suarez
 * @version 1.0.0
 * @since 24-Feb-2025
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>{

    List<Offer> findByBrandIdAndProductPartNumber(Long brandId, String partNumber);
    
}