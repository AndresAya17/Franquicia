package com.nequi.ms_franquicias.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.nequi.ms_franquicias.entities.Producto;
import com.nequi.ms_franquicias.entities.ProductoDto;
import com.nequi.ms_franquicias.entities.Sucursal;
import com.nequi.ms_franquicias.entities.SucursalDto;
import com.nequi.ms_franquicias.exceptions.IdNotFoundException;
import com.nequi.ms_franquicias.repository.ISucursalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ISucursalImpl implements ISucursalService {

    private final ISucursalRepository sucursalRepository;

    @Override
    public void save(Sucursal sucursal) {
        sucursalRepository.save(sucursal);
    }

    @Override
    public List<SucursalDto> findAll() {
        // Convierte todas las sucursales a SucursalDto
        return StreamSupport.stream(sucursalRepository.findAll().spliterator(), false)
                .map(this::convertToDto) // Convierte cada sucursal a SucursalDto
                .collect(Collectors.toList());
    }

    private SucursalDto convertToDto(Sucursal sucursal) {
        // Convertir los productos asociados a ProductoDto
        List<ProductoDto> productosDto = sucursal.getProductos().stream()
                .map(this::convertProductoToDto) // Convierte cada producto a ProductoDto
                .collect(Collectors.toList());

        // Crear y retornar un SucursalDto
        return new SucursalDto(sucursal.getNombre(), productosDto);
    }

    private ProductoDto convertProductoToDto(Producto producto) {
        // Crear un ProductoDto con los campos que necesitas, por ejemplo, nombre y
        // stock
        return new ProductoDto(producto.getNombre(), producto.getStock()); 
    }

    @Override
    public Sucursal findById(Long id) {
        var sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id));

        return sucursal;
    }

    @Override
    public void deleteById(Long id) {
        if (!sucursalRepository.existsById(id)) {
            throw new IdNotFoundException(id);
        }
        sucursalRepository.deleteById(id);
    }

    @Override
    public List<Sucursal> findByIdFranquicia(Long idFranquicia) {
        List<Sucursal> sucursales = sucursalRepository.findByFranquiciaIdFranquicia(idFranquicia);

        return sucursales;
    }

}
