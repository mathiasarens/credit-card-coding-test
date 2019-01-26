package de.arens.publics.sapient.model;

import de.arens.publics.sapient.validaton.Luhn10;
import de.arens.publics.sapient.validaton.ordersequence.FirstStep;
import de.arens.publics.sapient.validaton.ordersequence.OrderSequence;
import de.arens.publics.sapient.validaton.ordersequence.SecondStep;
import de.arens.publics.sapient.validaton.ordersequence.ThridStep;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class CreditCard {

    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private final Long id;
    @NotNull(groups = FirstStep.class)
    @Size(min = 1, message = "Please provide a name", groups = SecondStep.class)
    private final String name;
    @NotNull(groups = FirstStep.class)
    @Size(min = 16, max = 19, message = "Please provide a credit card number with 16 to 19 digits", groups = SecondStep.class)
    @Luhn10(message="Invalid credit card number", groups = ThridStep.class)
    private final String number;
    private final double balance;
    @Column(name="CREDIT_LIMIT")
    @Min(value = 0, message="Please provide a limit or 0 for no limit", groups = SecondStep.class)
    private final double limit;

    public CreditCard(final Long id, final String name, final String number, final double balance, final double limit) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.balance = balance;
        this.limit = limit;
    }

    public CreditCard(long id, String name, String number) {
        this(id, name, number, 0, 0);
    }

    public CreditCard() {
        this(null, null, null, 0, 0);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public double getLimit() {
        return limit;
    }
}
