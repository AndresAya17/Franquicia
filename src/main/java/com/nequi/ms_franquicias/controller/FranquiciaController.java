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
import com.nequi.ms_franquicias.exceptions.IdNotFoundException;
import com.nequi.ms_franquicias.service.IFranquiciaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/franquicia")
@RequiredArgsConstructor
public class FranquiciaController {

    private final IFranquiciaService franquiciaService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> SaveFranquicia(@Valid @RequestBody Franquicia franquicia, BindingResult bindingResult) {

        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasFieldErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            response.put("Message", errors.toString());
            return ResponseEntity.badRequest().body(errors);
        }

        response.put("Message", "Exitoso");
        franquiciaService.save(franquicia);
        return ResponseEntity.ok(response);
    }

    
     @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(franquiciaService.findAll());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(franquiciaService.findById(id));
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(IdNotFoundException ex) {
        // Retorna un 404 con el mensaje de la excepción
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @PostMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        franquiciaService.deleteById(id);
    }
    
}
