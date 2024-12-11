package com.nequi.ms_franquicias.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.nequi.ms_franquicias.entities.Franquicia;
import com.nequi.ms_franquicias.entities.FranquiciaDto;
import com.nequi.ms_franquicias.entities.Producto;
import com.nequi.ms_franquicias.entities.ProductoDto;
import com.nequi.ms_franquicias.entities.Sucursal;
import com.nequi.ms_franquicias.entities.SucursalDto;
import com.nequi.ms_franquicias.exceptions.IdNotFoundException;
import com.nequi.ms_franquicias.repository.IFranquiciaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IFranquiciaImpl implements IFranquiciaService {

    private final IFranquiciaRepository franquiciaRepository;

    @Override
    public void save(Franquicia franquicia) {
        franquiciaRepository.save(franquicia);
    }

    @Override
    public List<FranquiciaDto> findAll() {
        return StreamSupport.stream(franquiciaRepository.findAll().spliterator(), false)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FranquiciaDto convertToDto(Franquicia franquicia) {
        // Convertir la lista de Sucursales a SucursalDto
        List<SucursalDto> sucursalesDto = franquicia.getSucursales().stream()
                .map(this::convertSucursalToDto)
                .collect(Collectors.toList());

        // Devolver el DTO de Franquicia con la lista de Sucursales DTO
        return new FranquiciaDto(franquicia.getNombre(), sucursalesDto);
    }

    private SucursalDto convertSucursalToDto(Sucursal sucursal) {
        // Convertir la lista de productos a una lista de ProductoDto
        List<ProductoDto> productosDto = sucursal.getProductos().stream()
                .map(this::convertProductoToDto)
                .collect(Collectors.toList());

        // Crear y devolver el DTO de Sucursal con la lista de ProductoDto
        return new SucursalDto(sucursal.getNombre(), productosDto);
    }

    private ProductoDto convertProductoToDto(Producto producto) {
        // Convertir cada Producto a ProductoDto
        return new ProductoDto(producto.getNombre(), producto.getStock());
    }

    @Override
    public Franquicia findById(Long id) {
        var franquicia = franquiciaRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id));

        return franquicia;
    }

    @Override
    public void deleteById(Long id) {
        if (!franquiciaRepository.existsById(id)) {
            throw new IdNotFoundException(id);
        }
        franquiciaRepository.deleteById(id);
    }

    @Override
    public FranquiciaDto updateName(Long id, String nombre) {
        Optional<Franquicia> franquiciaOptional = franquiciaRepository.findById(id);
        if (franquiciaOptional.isPresent()) {
            Franquicia franquicia = franquiciaOptional.get();
            franquicia.setNombre(nombre);

            // Guardar la entidad actualizada en la base de datos
            Franquicia franquiciaGuardada = franquiciaRepository.save(franquicia);

            // Convertir la entidad actualizada a FranquiciaDto y devolverla
            return convertToDto(franquiciaGuardada);
        }
        return null;
    }

}
