package com.nequi.ms_franquicias.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nequi.ms_franquicias.entities.Franquicia;
import com.nequi.ms_franquicias.entities.Sucursal;
import com.nequi.ms_franquicias.entities.SucursalDto;
import com.nequi.ms_franquicias.exceptions.IdNotFoundException;
import com.nequi.ms_franquicias.service.IFranquiciaService;
import com.nequi.ms_franquicias.service.ISucursalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sucursal")
@RequiredArgsConstructor
public class SucursalController {

    private final ISucursalService sucursalService;
    private final IFranquiciaService franquiciaService;
    
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> SaveSucursal(@Valid @RequestBody Sucursal sucursal, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        // Validar errores en la solicitud
        if (bindingResult.hasFieldErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            response.put("Message", errors.toString());
            return ResponseEntity.badRequest().body(errors);
        }

        // Verificar que la franquicia exista usando el servicio
        Franquicia franquicia = franquiciaService.findById(sucursal.getFranquicia().getIdFranquicia());

        // Asociar la franquicia a la sucursal
        sucursal.setFranquicia(franquicia);

        // Guardar la sucursal
        sucursalService.save(sucursal);

        response.put("Message", "Exitoso");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(sucursalService.findAll());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(sucursalService.findById(id));
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(IdNotFoundException ex) {
        // Retorna un 404 con el mensaje de la excepción
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @PostMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        sucursalService.deleteById(id);
    }

    @GetMapping("/franquicia/{idFranquicia}")
    public ResponseEntity<List<Sucursal>> getSucursalesByFranquicia(@PathVariable Long idFranquicia) {
        List<Sucursal> sucursal = sucursalService.findByIdFranquicia(idFranquicia);
        return ResponseEntity.ok(sucursal);
    }
}
