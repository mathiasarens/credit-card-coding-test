package de.arens.publics.sapient.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class Luhn10Validator implements ConstraintValidator<Luhn10, String> {

    @Override
    public void initialize(Luhn10 constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return luhnCheck(s);
    }

    /**
     * Checks if the card is valid
     *
     * @param card {@link String} card number
     * @return result {@link boolean} true of false
     */
    public boolean luhnCheck(String card) {
        if (card == null || card.length() <= 1)
            return false;
        char checkDigit = card.charAt(card.length() - 1);
        char digit = calculateCheckDigit(card.substring(0, card.length() - 1));
        return checkDigit==digit;
    }

    /**
     * Calculates the last digits for the card number received as parameter
     *
     * @param card {@link String} number
     * @return {@link String} the check digit
     */
    public char calculateCheckDigit(String card) {
        /* convert to array of int for simplicity */
        int[] digits = new int[card.length()];
        for (int i = 0; i < card.length(); i++) {
            digits[i] = Character.getNumericValue(card.charAt(i));
        }

        /* double every other starting from right - jumping from 2 in 2 */
        for (int i = digits.length - 1; i >= 0; i -= 2) {
            digits[i] += digits[i];

            /* taking the sum of digits grater than 10 - simple trick by substract 9 */
            if (digits[i] >= 10) {
                digits[i] = digits[i] - 9;
            }
        }
        int sum = Arrays.stream(digits).sum() * 9;

        /* convert to string to be easier to take the last digit */
        return String.valueOf(sum % 10).charAt(0);
    }


}
