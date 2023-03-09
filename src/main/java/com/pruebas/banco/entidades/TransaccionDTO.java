package com.pruebas.banco.entidades;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class TransaccionDTO {
    @Getter @Setter
    private Long cuentaOrigenId;
    @Getter @Setter
    private Long cuentaDestinoId;
    @Getter @Setter
    private BigDecimal monto;
    @Getter @Setter
    private Long bancoId;
}
