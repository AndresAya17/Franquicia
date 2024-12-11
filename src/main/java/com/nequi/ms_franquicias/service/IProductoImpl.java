package com.nequi.ms_franquicias.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.nequi.ms_franquicias.entities.Producto;
import com.nequi.ms_franquicias.entities.ProductoConSucursalDto;
import com.nequi.ms_franquicias.entities.ProductoDto;
import com.nequi.ms_franquicias.entities.Sucursal;
import com.nequi.ms_franquicias.entities.SucursalDto;
import com.nequi.ms_franquicias.exceptions.IdNotFoundException;
import com.nequi.ms_franquicias.repository.IProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IProductoImpl implements IProductoService {

    private final IProductoRepository productoRepository;
    private final ISucursalService sucursalService;

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
        return new ProductoDto(producto.getNombre(), producto.getStock());
    }

    @Override
    public Producto findById(Long id) {
        // Encuentra el producto por id y si no lo encuentra lanza una excepción
        var producto = productoRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id)); // Aquí puedes usar orElseThrow()

        return producto;
    }

    @Override
    public void deleteById(Long id) {
        // Verifica si el producto existe antes de eliminarlo
        if (!productoRepository.existsById(id)) {
            throw new IdNotFoundException(id);
        }
        productoRepository.deleteById(id);
    }

    @Override
    public List<ProductoConSucursalDto> getProductosConMasStockPorFranquicia(Long idFranquicia) {
        // Obtener todas las sucursales de la franquicia
        List<Sucursal> sucursales = sucursalService.findByIdFranquicia(idFranquicia);
        
        List<ProductoConSucursalDto> productosConMasStock = new ArrayList<>();  // Lista de ProductoConSucursalDto
        
        // Iterar por cada sucursal y obtener el producto con más stock
        for (Sucursal sucursal : sucursales) {
            
            // Obtener el producto con más stock para la sucursal
            Producto productoConMasStock = productoRepository.findTopBySucursalOrderByStockDesc(sucursal);
            
            if (productoConMasStock != null) {
                
                // Convertir el Producto a ProductoConSucursalDto antes de agregarlo
                ProductoConSucursalDto productoConSucursalDto = new ProductoConSucursalDto(
                        productoConMasStock.getNombre(), 
                        productoConMasStock.getStock(),
                        sucursal.getNombre() // Pasar el nombre de la sucursal
                );
                
                // Añadir el ProductoConSucursalDto a la lista
                productosConMasStock.add(productoConSucursalDto);
            } else {
                System.out.println("No se encontró producto con stock para la sucursal: " + sucursal.getNombre());
            }
        }
        
        return productosConMasStock;  // Retornar la lista de ProductoConSucursalDto
    }
}
