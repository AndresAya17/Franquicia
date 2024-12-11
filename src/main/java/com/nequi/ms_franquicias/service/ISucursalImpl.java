package com.nequi.ms_franquicias.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.nequi.ms_franquicias.entities.Producto;
import com.nequi.ms_franquicias.entities.ProductoDto;
import com.nequi.ms_franquicias.entities.Sucursal;
import com.nequi.ms_franquicias.entities.SucursalDto;
import com.nequi.ms_franquicias.exceptions.IdNotFoundException;
import com.nequi.ms_franquicias.repository.IProductoRepository;
import com.nequi.ms_franquicias.repository.ISucursalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ISucursalImpl implements ISucursalService {

    private final ISucursalRepository sucursalRepository;
    private final IProductoRepository productoRepository;

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

    @Override
    public SucursalDto updateName(Long id, String nombre) {
        Optional<Sucursal> sucursalOptional = sucursalRepository.findById(id);

        if(sucursalOptional.isPresent()){
            Sucursal sucursal =sucursalOptional.get();
            sucursal.setNombre(nombre);

            Sucursal sucursalGuardada = sucursalRepository.save(sucursal);

            return convertToDto(sucursalGuardada);
        }
        return null;
    }

    @Override
    public void deleteByIdProducto(Long idSucursal, Long idProducto) {
        // Buscar la sucursal
        Sucursal sucursal = sucursalRepository.findById(idSucursal)
                .orElseThrow(() -> new IdNotFoundException(idSucursal));

        // Buscar el producto en esa sucursal
        Producto producto = productoRepository.findById(idProducto)
                .filter(p -> p.getSucursal().equals(sucursal))
                .orElseThrow(() -> new IdNotFoundException(idProducto));

        // Eliminar el producto
        productoRepository.delete(producto);
    }
    

}
