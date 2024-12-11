package com.nequi.ms_franquicias.service;

import java.util.List;

import com.nequi.ms_franquicias.entities.Sucursal;

public interface ISucursalService {
    void save (Sucursal sucursal);

    List<Sucursal> findAll();

    Sucursal findById(Long id);

    void deleteById(Long id);
}
