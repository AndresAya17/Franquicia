package com.nequi.ms_franquicias.entities;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "franquicia")
@NoArgsConstructor
@AllArgsConstructor
public class Franquicia {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFranquicia;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "franquicia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sucursal> sucursales;
}
