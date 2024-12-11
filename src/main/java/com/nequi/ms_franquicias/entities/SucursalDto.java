package com.nequi.ms_franquicias.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDto {

    private String nombre;
    private List<ProductoDto> productos;
    
}
