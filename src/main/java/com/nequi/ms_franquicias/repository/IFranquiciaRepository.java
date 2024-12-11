package com.nequi.ms_franquicias.repository;

import org.springframework.data.repository.CrudRepository;

import com.nequi.ms_franquicias.entities.Franquicia;

public interface IFranquiciaRepository extends CrudRepository<Franquicia, Long>{
    
}
