package com.pruebas.banco.repositorios;

import com.pruebas.banco.entidades.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CuentaRepositorio extends JpaRepository<Cuenta,Long> {
    @Query("select c from Cuenta c where c.persona=?1")
    public Optional<Cuenta> findByPersona(String persona);
}
