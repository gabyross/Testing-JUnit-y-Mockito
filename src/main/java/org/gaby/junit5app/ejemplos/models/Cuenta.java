package org.gaby.junit5app.ejemplos.models;

import java.math.BigDecimal;
//import java.util.Locale;

public class Cuenta {
    private String persona;
    private BigDecimal saldo;

    public Cuenta(String persona, BigDecimal saldo) {
        //this.persona = persona.toUpperCase(); simulacion de falla
        this.persona = persona;
        this.saldo = saldo;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void  debito(BigDecimal monto){
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        this.saldo = nuevoSaldo;

    }

    public void  credito(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }

    @Override
    // en vez de comparar instancias, compara el objeto
    public boolean equals(Object obj) {
        if (!(obj instanceof Cuenta)) {
            // el objeto no puede ser nulo y debe ser de tipo cuenta
            return false;
        }
        Cuenta c = (Cuenta) obj;
        if (this.persona == null || this.saldo == null){
            return false;
        }
        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
    }
}
