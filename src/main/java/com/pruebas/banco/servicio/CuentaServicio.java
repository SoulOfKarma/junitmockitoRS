package com.pruebas.banco.servicio;

import com.pruebas.banco.entidades.Cuenta;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaServicio {

    public List<Cuenta> listAll();

    public Cuenta findById(Long id);

    public Cuenta save(Cuenta cuenta);

    public int revisarTotalDeTransferencias(Long bancoId);

    public BigDecimal revisarSaldo(Long cuentaId);

    public void transferirDinero(Long numeroCuentaOrigen,Long numeroCuentaDestino,BigDecimal monto,Long bancoId);
}
