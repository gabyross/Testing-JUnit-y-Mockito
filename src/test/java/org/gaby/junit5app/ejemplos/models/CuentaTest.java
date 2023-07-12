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

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta1 = new Cuenta("Gaby", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("Gaby", new BigDecimal("8900.9997"));

        /* si no se encuentra comentado el override de la funcion equal, falla
        sino, funciona ya que son dos referencias distintas */
        // assertNotEquals(cuenta1, cuenta2);

        /* si se encuentra comentado el override de la funcion equal ocasiona falla ya que son dos instancias distintas
        sino, no falla porque en realidad comparamos el contenido y no las referencias */
        assertEquals(cuenta1, cuenta2);
    }
}