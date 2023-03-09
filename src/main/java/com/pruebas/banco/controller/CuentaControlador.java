package com.pruebas.banco.controller;

import com.pruebas.banco.entidades.Cuenta;
import com.pruebas.banco.entidades.TransaccionDTO;
import com.pruebas.banco.servicio.CuentaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/cuentas")
public class CuentaControlador {

    @Autowired
    private CuentaServicio cuentaServicio;

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Cuenta verDetalles(@PathVariable Long id){
        return cuentaServicio.findById(id);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferirDinero(@RequestBody TransaccionDTO transaccionDTO){
        cuentaServicio.transferirDinero(transaccionDTO.getCuentaOrigenId(),transaccionDTO.getCuentaDestinoId(),transaccionDTO.getMonto(),transaccionDTO.getBancoId());
        Map<String,Object> respuesta = new HashMap<>();
        respuesta.put("date", LocalDate.now().toString());
        respuesta.put("status","OK");
        respuesta.put("Mensaje","Transferencia Realizada con exito");
        respuesta.put("transaccionDTO",transaccionDTO);

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/cuentas")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Cuenta> listarCuentas(){
        return cuentaServicio.listAll();
    }
    @PostMapping("/guardar")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Cuenta guardarCuenta(@RequestBody Cuenta cuenta){
        return cuentaServicio.save(cuenta);
    }

}
