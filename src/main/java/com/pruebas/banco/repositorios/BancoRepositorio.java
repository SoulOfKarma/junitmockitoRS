package com.pruebas.banco.repositorios;

import com.pruebas.banco.entidades.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepositorio extends JpaRepository<Banco,Long> {
}
