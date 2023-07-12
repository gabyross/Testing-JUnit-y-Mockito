package models;


import exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    @DisplayName("probando el nombre de la cuenta")
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Gaby", new BigDecimal("1000.12345"));
        //cuenta.setPersona("Gaby");
        String esperado = "Gaby";
        String real = cuenta.getPersona();
        assertNotNull(real, () -> "La cuenta no puede ser nula"); // debe existir persona, no puede ser nulo
        Assertions.assertEquals(esperado, real, () -> "El nombre de la cuenta no es el que se esperaba");
        assertEquals("Gaby", real, () -> "Nombre cuenta esperada debe ser igual a la real"); // si el real es upperCase, falla
        assertTrue(real.equals("Gaby"), () -> "Nombre cuenta esperada debe ser igual a la real"); // si el real es upperCase, falla
    }

    @Test
    @DisplayName("probando el saldo de la cuenta")
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Gaby", new BigDecimal("1000.12345"));
        // Cuenta cuenta = new Cuenta("Gaby", new BigDecimal("-1000.12345")); // simula falla por saldo menor a cero
        assertNotNull(cuenta.getSaldo()); // verifica que el saldo no sea nulo
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0); // saldo < 0 da error
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0); // saldo < 0 da error
    }

    @Test
    @DisplayName("testeando referencias que sean iguales con el metodo equals")
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
    @DisplayName("probando debitar de una cuenta")
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
    @DisplayName("probando excepeciones de monto en cuenta")
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

    @Test
    @DisplayName("probando transferir dinero entre cuentas")
    void testTransferirDineroCuenta() {
        Cuenta cuenta1 = new Cuenta("Soto", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Gaby", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    @Disabled // hace que se salte la prueba de este test
    @DisplayName("probando relaciones entre las cuentas y el banco con assertAll")
    void testRelacionBancoCuentas() {
        fail(); // forza que falle el test
        Cuenta cuenta1 = new Cuenta("Soto", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Gaby", new BigDecimal("1500.8989"));

        Banco banco = new Banco();

        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta1, cuenta2, new BigDecimal(500));

        assertAll(
                () -> {
                    assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(),
                            () -> "El valor de saldo de la cuenta2 no es el esperado");
                },
                () -> {
                    assertEquals("3000", cuenta1.getSaldo().toPlainString(),
                            () -> "El valor de saldo de la cuenta1 no es el esperado");
                },
                () -> {
                    assertEquals(2, banco.getCuentas().size(),
                            () -> "El banco no tiene las cuentas esperadas");
                },
                () -> {
                    assertEquals("Banco del Estado.", cuenta1.getBanco().getNombre());
                },

                // encuentra el usuario gaby en la lista de cuentas del banco
                () -> {
                    assertEquals("Gaby", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Gaby"))
                            .findFirst().get().getPersona()
                    );
                },

                // ve si hay algun match con gaby en la lista de cuentas del banco
                () -> {
                    assertTrue(banco.getCuentas().stream()
                            .anyMatch(c -> c.getPersona().equals("Gaby"))
                    );
                }
        );
    }


}
