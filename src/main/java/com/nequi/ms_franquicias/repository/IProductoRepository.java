package com.nequi.ms_franquicias.repository;

import org.springframework.data.repository.CrudRepository;

import com.nequi.ms_franquicias.entities.Producto;

public interface IProductoRepository extends CrudRepository<Producto, Long>{
    
}
