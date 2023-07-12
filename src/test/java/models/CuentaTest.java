package models;


import exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
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
        assertNotNull(real); // debe existir persona, no puede ser nulo
        Assertions.assertEquals(esperado, real);
        assertEquals("Gaby", real); // si el real es upperCase, falla
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Gaby", new BigDecimal("1000.12345"));
        // Cuenta cuenta = new Cuenta("Gaby", new BigDecimal("-1000.12345")); // simula falla por saldo menor a cero
        assertNotNull(cuenta.getSaldo()); // verifica que el saldo no sea nulo
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

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Gaby", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100)); // se le resta 100 a la cuenta corriente

        // cuenta.debito(new BigDecimal(2000)); // tira exception por dinero insuficiente

        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue()); // es correcto

        /* verifico si el saldo es igual en strings
        como habia restado 100, el valor actual es 900 y no 1000 */
        assertEquals("900.12345", cuenta.getSaldo().toPlainString()); // es correcto
        //assertEquals("1000.12345", cuenta.getSaldo().toPlainString()); // genera falla
    }

    @Test
    @Tag("cuenta")
    @Tag("error")
    void testDineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Gaby", new BigDecimal("1000.12345"));

        // simulamos el error, en la cuenta no hay 1500
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";

        //si cambio el mensaje no pasa la prueba
        // String esperado = "Dinero Insuficiente en la cuenta"; // falla
        assertEquals(esperado, actual);
    }

}
