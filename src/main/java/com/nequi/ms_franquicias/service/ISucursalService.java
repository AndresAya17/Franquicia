package com.nequi.ms_franquicias.service;

import java.util.List;

import com.nequi.ms_franquicias.entities.Sucursal;
import com.nequi.ms_franquicias.entities.SucursalDto;

public interface ISucursalService {
    void save (Sucursal sucursal);

    List<SucursalDto> findAll();

    List<Sucursal> findByIdFranquicia(Long idFranquicia);

    Sucursal findById(Long id);

    void deleteById(Long id);
}
