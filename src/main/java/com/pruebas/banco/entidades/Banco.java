package com.pruebas.banco.entidades;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "bancos")
@ToString @EqualsAndHashCode
public class Banco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name = "id")
    private Long id;
    @Getter @Setter @Column(name = "nombre")
    private String nombre;
    @Getter @Setter @Column(name = "total_transferencias")
    private int totalTransferencias;

    public Banco(){}

    public Banco(Long id, String nombre, int totalTransferencias){
        super();
        this.id = id;
        this.nombre = nombre;
        this.totalTransferencias = totalTransferencias;
    }


}
