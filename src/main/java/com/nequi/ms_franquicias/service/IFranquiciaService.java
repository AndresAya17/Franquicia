package com.nequi.ms_franquicias.service;

import java.util.List;

import com.nequi.ms_franquicias.entities.Franquicia;

public interface IFranquiciaService {

    void save (Franquicia franquicia);

    List<Franquicia> findAll();

    Franquicia findById(Long id);

    void deleteById(Long id);
    
}
