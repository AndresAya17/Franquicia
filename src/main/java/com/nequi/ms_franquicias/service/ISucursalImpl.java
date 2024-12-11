package com.nequi.ms_franquicias.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.nequi.ms_franquicias.entities.Sucursal;
import com.nequi.ms_franquicias.exceptions.UserNotFoundException;
import com.nequi.ms_franquicias.repository.ISucursalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ISucursalImpl implements ISucursalService{

    private final ISucursalRepository sucursalRepository;

    @Override
    public void save(Sucursal sucursal) {
        sucursalRepository.save(sucursal);
    }

    @Override
    public List<Sucursal> findAll() {
         return StreamSupport.stream(sucursalRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Sucursal findById(Long id) {
        var sucursal = sucursalRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

        return sucursal;
    }

    @Override
    public void deleteById(Long id) {
        if(!sucursalRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        sucursalRepository.deleteById(id);
    }
    
}