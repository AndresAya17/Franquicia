package com.nequi.ms_franquicias.service;

import java.util.List;

import com.nequi.ms_franquicias.entities.Producto;
import com.nequi.ms_franquicias.entities.ProductoConSucursalDto;
import com.nequi.ms_franquicias.entities.ProductoDto;

public interface IProductoService {
    void save (Producto producto);

    List<ProductoDto> findAll();

    Producto findById(Long id);

    List<ProductoConSucursalDto> getProductosConMasStockPorFranquicia(Long idFranquicia);

    void deleteById(Long id);
}
