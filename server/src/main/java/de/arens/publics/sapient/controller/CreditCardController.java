package de.arens.publics.sapient.controller;

import de.arens.publics.sapient.error.ApiError;
import de.arens.publics.sapient.model.CreditCard;
import de.arens.publics.sapient.repository.CreditCardRepository;
import de.arens.publics.sapient.validaton.ordersequence.OrderSequence;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin(origins = {"*"})
@RestController
public class CreditCardController {

    static final String CREDIT_CARD_URL = "/creditcard";

    @Resource
    private CreditCardRepository creditCardRepository;

    @PostMapping(CREDIT_CARD_URL)
    public ResponseEntity<?> add(@Validated(OrderSequence.class) @RequestBody final CreditCard creditCard) {
        if (creditCard.getId() != null) {
            return ResponseEntity.status(303).body(String.format("Please update credit card resource via %s/%d",
                    CREDIT_CARD_URL, creditCard.getId()));
        }
        try {
            final CreditCard savedCreditCard = creditCardRepository.save(creditCard);
            return new ResponseEntity(savedCreditCard, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains(CreditCard.UC_CREDITCARD_NUMBER)) {
                return buildErrorResponseEntity("number", creditCard.getNumber(),
                        "A credit card with that number already exists");
            }
            if (e.getMessage().contains(CreditCard.UC_CREDITCARD_NAME)) {
                return buildErrorResponseEntity("name", creditCard.getName(),
                        "A credit card with that name already exists");
            }
            throw e;
        }
    }

    @GetMapping(value = CREDIT_CARD_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<CreditCard> list() {
        return creditCardRepository.findAll();
    }

    private ResponseEntity<?> buildErrorResponseEntity(final String field, final String rejectedValue, final String message) {
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationError("creditCard", field, rejectedValue, message);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
