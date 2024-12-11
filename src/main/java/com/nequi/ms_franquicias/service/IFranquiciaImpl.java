package com.nequi.ms_franquicias.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.nequi.ms_franquicias.entities.Franquicia;
import com.nequi.ms_franquicias.exceptions.UserNotFoundException;
import com.nequi.ms_franquicias.repository.IFranquiciaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IFranquiciaImpl implements IFranquiciaService{

    private final IFranquiciaRepository franquiciaRepository;

    @Override
    public void save(Franquicia franquicia) {
        franquiciaRepository.save(franquicia);
    }

    @Override
    public List<Franquicia> findAll() {
        return StreamSupport.stream(franquiciaRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Franquicia findById(Long id) {
        var franquicia = franquiciaRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

        return franquicia;
    }

    @Override
    public void deleteById(Long id) {
        if(!franquiciaRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        franquiciaRepository.deleteById(id);
    }
    
}
