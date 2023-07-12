package org.gaby.junit5app.ejemplos.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta(){
        Cuenta cuenta = new Cuenta("Gaby", new BigDecimal("1000.12345"));
        //cuenta.setPersona("Gaby");
        String esperado = "Gaby";
        String real = cuenta.getPersona();
        Assertions.assertEquals(esperado, real);
        assertEquals("Gaby", real); // si el real es upperCase, falla
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Gaby", new BigDecimal("1000.12345"));
        // Cuenta cuenta = new Cuenta("Gaby", new BigDecimal("-1000.12345")); // simula falla por saldo menor a cero
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0); // saldo < 0 da error
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0); // saldo < 0 da error
    }

}