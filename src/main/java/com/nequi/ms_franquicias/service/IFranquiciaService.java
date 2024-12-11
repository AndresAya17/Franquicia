package com.nequi.ms_franquicias.service;

import java.util.List;

import com.nequi.ms_franquicias.entities.Franquicia;
import com.nequi.ms_franquicias.entities.FranquiciaDto;

public interface IFranquiciaService {

    void save (Franquicia franquicia);

    List<FranquiciaDto> findAll();

    Franquicia findById(Long id);

    void deleteById(Long id);
    
}
