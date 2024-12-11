package com.nequi.ms_franquicias.service;

import java.util.List;

import com.nequi.ms_franquicias.entities.Sucursal;
import com.nequi.ms_franquicias.entities.SucursalDto;

public interface ISucursalService {
    void save (Sucursal sucursal);

    List<SucursalDto> findAll();

    SucursalDto updateName(Long id, String nombre); 

    List<Sucursal> findByIdFranquicia(Long idFranquicia);

    Sucursal findById(Long id);

    void deleteById(Long id);

    void deleteByIdProducto(Long idSucursal, Long idProducto);
}
