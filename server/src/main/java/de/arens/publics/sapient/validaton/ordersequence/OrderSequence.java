package de.arens.publics.sapient.validaton.ordersequence;

import javax.validation.GroupSequence;

@GroupSequence({FirstStep.class, SecondStep.class, ThridStep.class})
public interface OrderSequence {
}
