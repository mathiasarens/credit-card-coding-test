package de.arens.publics.sapient.validaton;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Luhn10ValidatorTest {

    private Luhn10Validator validator;

    @Before
    public void setup() {
        validator = new Luhn10Validator();
    }

    @Test
    public void nullInputShouldReturnFalse() {
        assertFalse(validator.luhnCheck(null));
    }

    @Test
    public void inputWithStringCharactersShouldReturnFalse() {
        assertFalse(validator.luhnCheck("shsdfsvsdfsdfertetfgd"));
    }

    @Test
    public void creditCardNumberWithWrongChecksumShouldReturnFalse() {
        assertFalse(validator.luhnCheck("0350847183161940092"));
    }

    @Test
    public void correctCreditCardNumberShouldReturnTrue() {
        assertTrue(validator.luhnCheck("0350847183161940093"));
    }
}