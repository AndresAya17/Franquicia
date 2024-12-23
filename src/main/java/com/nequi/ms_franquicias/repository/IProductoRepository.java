package com.nequi.ms_franquicias.repository;

import org.springframework.data.repository.CrudRepository;

import com.nequi.ms_franquicias.entities.Producto;
import com.nequi.ms_franquicias.entities.Sucursal;

public interface IProductoRepository extends CrudRepository<Producto, Long>{

    Producto findTopBySucursalOrderByStockDesc(Sucursal sucursal);
    
}
