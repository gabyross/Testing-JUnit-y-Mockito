package org.gaby.junit5app.ejemplos.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta(){
        Cuenta cuenta = new Cuenta("Gaby", new BigDecimal("1000.12345"));
        cuenta.setPersona("Gaby");
        String esperado = "Gaby";
        String real = cuenta.getPersona();
        Assertions.assertEquals(esperado, real);
        assertTrue(real.equals("Gaby"));
    }

}