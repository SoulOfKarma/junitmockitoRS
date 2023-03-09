package com.pruebas.banco.entidades;

import com.pruebas.banco.excepciones.DineroInsuficienteException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cuentas")
@ToString @EqualsAndHashCode
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name = "id")
    private Long id;
    @Getter @Setter @Column(name = "persona")
    private String persona;
    @Getter @Setter @Column(name = "saldo")
    private BigDecimal saldo;

    public Cuenta(){

    }

    public Cuenta(Long id,String persona,BigDecimal saldo){
        super();
        this.id = id;
        this.persona = persona;
        this.saldo = saldo;
    }

    public void realizarDebito(BigDecimal monto){
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0){
            throw new DineroInsuficienteException("Dinero Insuficiente en la cuenta");
        }
        this.saldo = nuevoSaldo;
    }

    public void realizarCredito(BigDecimal monto){
        this.saldo = saldo.add(monto);
    }


}
