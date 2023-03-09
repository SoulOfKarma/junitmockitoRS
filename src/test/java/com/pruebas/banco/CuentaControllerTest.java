package com.pruebas.banco;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebas.banco.controller.CuentaControlador;
import com.pruebas.banco.entidades.Cuenta;
import com.pruebas.banco.entidades.TransaccionDTO;
import com.pruebas.banco.servicio.CuentaServicio;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

//import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CuentaControlador.class)
public class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaServicio cuentaServicio;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void testVerDetalles() throws Exception {
        when(cuentaServicio.findById(1L)).thenReturn(Datos.crearCuenta001().orElseThrow());

        mockMvc.perform(get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.persona").value("Christian"))
                .andExpect(jsonPath("$.saldo").value("1000"));

        verify(cuentaServicio).findById(1L);
    }

    @Test
    void testTransferirDinero() throws Exception {
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        transaccionDTO.setCuentaOrigenId(1L);
        transaccionDTO.setCuentaDestinoId(2L);
        transaccionDTO.setMonto(new BigDecimal("100"));
        transaccionDTO.setBancoId(1L);

        System.out.println(objectMapper.writeValueAsString(transaccionDTO));

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("date", LocalDate.now().toString());
        respuesta.put("status","OK");
        respuesta.put("Mensaje","Transferencia Realizada con exito");
        respuesta.put("transaccionDTO",transaccionDTO);

        System.out.println(objectMapper.writeValueAsString(respuesta));

        mockMvc.perform(post("/api/cuentas/transferir").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaccionDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.Mensaje").value("Transferencia Realizada con exito"))
                .andExpect(jsonPath("$.transaccionDTO.cuentaOrigenId").value(transaccionDTO.getCuentaOrigenId()))
                .andExpect(content().json(objectMapper.writeValueAsString(respuesta)));

    }
    @Test
    void testListarCuentas() throws Exception {
       List<Cuenta> cuentas = Arrays.asList(Datos.crearCuenta001().orElseThrow(),Datos.crearCuenta002().orElseThrow());
       when(cuentaServicio.listAll()).thenReturn(cuentas);

       mockMvc.perform(get("/api/cuentas/cuentas").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].persona").value("Christian"))
               .andExpect(jsonPath("$[1].persona").value("Julen"))
               .andExpect(jsonPath("$[0].saldo").value("1000"))
               .andExpect(jsonPath("$[1].saldo").value("2000"))
               .andExpect(jsonPath("$",hasSize(2)))
               .andExpect(content().json(objectMapper.writeValueAsString(cuentas)));

       verify(cuentaServicio).listAll();

    }

    @Test
    void guardarCuenta() throws Exception {
        Cuenta cuenta = new Cuenta(null,"Biaggio",new BigDecimal("3000"));
        when(cuentaServicio.save(any())).then(invocation -> {
            Cuenta c = invocation.getArgument(0);
            c.setId(3L);
            return c;
        });

        mockMvc.perform(post("/api/cuentas/guardar").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuenta)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(3)))
                .andExpect(jsonPath("$.persona", is("Biaggio")))
                .andExpect(jsonPath("$.saldo",is(3000)));

        verify(cuentaServicio).save(any());
    }
}
