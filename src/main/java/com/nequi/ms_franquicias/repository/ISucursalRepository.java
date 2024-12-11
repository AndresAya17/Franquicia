package com.nequi.ms_franquicias.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nequi.ms_franquicias.entities.Sucursal;

public interface ISucursalRepository extends CrudRepository<Sucursal, Long>{

    List<Sucursal> findByFranquiciaIdFranquicia(Long idFranquicia);
    
}
