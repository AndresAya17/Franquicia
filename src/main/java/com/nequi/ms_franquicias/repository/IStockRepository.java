package com.nequi.ms_franquicias.repository;

import org.springframework.data.repository.CrudRepository;

import com.nequi.ms_franquicias.entities.Stock;

public interface IStockRepository extends CrudRepository<Stock, Long>{
    
}
