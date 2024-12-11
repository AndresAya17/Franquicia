package com.nequi.ms_franquicias.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoConSucursalDto {

    private String nombreProducto;
    private Long stock;
    private String nombreSucursal;
}