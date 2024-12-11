package com.nequi.ms_franquicias.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nequi.ms_franquicias.entities.Producto;
import com.nequi.ms_franquicias.entities.ProductoDto;
import com.nequi.ms_franquicias.entities.Sucursal;
import com.nequi.ms_franquicias.exceptions.IdNotFoundException;
import com.nequi.ms_franquicias.service.IProductoService;
import com.nequi.ms_franquicias.service.ISucursalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final IProductoService productoService;
    private final ISucursalService sucursalService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveProducto(@Valid @RequestBody Producto producto, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        // Verificar si hay errores de validación
        if (bindingResult.hasFieldErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            response.put("Message", errors.toString());
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            // Verificar si el producto tiene una sucursal asociada
            if (producto.getSucursal() != null && producto.getSucursal().getIdSucursal() != null) {
                // Obtener la sucursal usando su id y validarla
                Sucursal sucursal = sucursalService.findById(producto.getSucursal().getIdSucursal());
                System.out.println(sucursal);

                // Asignar la sucursal al producto
                producto.setSucursal(sucursal);
            } else {
                // Si no se proporciona sucursal, puedes lanzar un error o asignar una sucursal
                // predeterminada
                throw new IllegalArgumentException("Se debe proporcionar un idSucursal válido.");
            }

            // Guardar el nuevo producto
            productoService.save(producto);

            // Convertir Producto a ProductoDto antes de devolver la respuesta
            ProductoDto productoDto = convertToDto(producto);

            response.put("Message", "Producto creado exitosamente.");
            // response.put("Producto", productoDto); // Devolver el ProductoDto

            return ResponseEntity.ok(response);

        } catch (IdNotFoundException e) {
            response.put("Message", "Franquicia o Sucursal no encontrada.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalArgumentException e) {
            response.put("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Función para convertir Producto a ProductoDto
    private ProductoDto convertToDto(Producto producto) {
        // Asumiendo que el ProductoDto tiene un constructor que acepta el nombre y los
        // stocks
        return new ProductoDto(producto.getNombre(), producto.getStock());
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(IdNotFoundException ex) {
        // Retorna un 404 con el mensaje de la excepción
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        productoService.deleteById(id);
        response.put("Message", "Producto eliminado exitosamente.");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar que el stock esté presente en el cuerpo de la solicitud
            if (!request.containsKey("stock")) {
                response.put("Message", "El campo 'stock' es obligatorio.");
                return ResponseEntity.badRequest().body(response);
            }
    
            Integer newStock = request.get("stock");
    
            // Validar que el stock sea mayor o igual a cero
            if (newStock < 0) {
                response.put("Message", "El stock no puede ser un valor negativo.");
                return ResponseEntity.badRequest().body(response);
            }
    
            // Buscar el producto por su ID
            Producto producto = productoService.findById(id);
            if (producto == null) {
                response.put("Message", "Producto no encontrado.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
    
            // Actualizar el stock
            producto.setStock(newStock.longValue());
            productoService.save(producto);
    
            response.put("Message", "Stock actualizado exitosamente.");
            // response.put("Producto", producto);
            return ResponseEntity.ok(response);
    
        } catch (Exception e) {
            response.put("Message", "Ocurrió un error al actualizar el stock.");
            response.put("Error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Endpoint para obtener los productos con más stock por franquicia
    @GetMapping("/mas-stock/{idFranquicia}")
    public ResponseEntity<List<ProductoDto>> getProductosConMasStockPorFranquicia(@PathVariable Long idFranquicia) {
        try {
            // Llamar al servicio para obtener los productos con más stock por franquicia
            List<ProductoDto> productos = productoService.getProductosConMasStockPorFranquicia(idFranquicia);
            System.out.println(productos);

            // Si no se encuentran productos, retornar un no content
            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // Retornar los productos con más stock en una respuesta OK
            return ResponseEntity.ok(productos);

        } catch (Exception e) {
            // Manejo de errores: retornar un internal server error si ocurre una excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
