package com.nequi.ms_franquicias.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.nequi.ms_franquicias.entities.Producto;
import com.nequi.ms_franquicias.entities.ProductoDto;
import com.nequi.ms_franquicias.exceptions.UserNotFoundException;
import com.nequi.ms_franquicias.repository.IProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IProductoImpl implements IProductoService {

    private final IProductoRepository productoRepository;

    @Override
    public void save(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public List<ProductoDto> findAll() {
        // Convierte cada Producto a ProductoDto y devuelve la lista resultante
        return StreamSupport.stream(productoRepository.findAll().spliterator(), false)
                .map(this::convertToDto) // Convierte el Producto a ProductoDto
                .collect(Collectors.toList()); // Recoge todos los Dto en una lista
    }

    private ProductoDto convertToDto(Producto producto) {
        // Crea el ProductoDto y asigna los campos que necesitas (nombre y stock)
        return new ProductoDto(producto.getNombre(),producto.getStock());
    }

    @Override
    public Producto findById(Long id) {
        // Encuentra el producto por id y si no lo encuentra lanza una excepción
        var producto = productoRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id)); // Aquí puedes usar orElseThrow()
        
        return producto;
    }

    @Override
    public void deleteById(Long id) {
        // Verifica si el producto existe antes de eliminarlo
        if (!productoRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        productoRepository.deleteById(id);
    }
}
