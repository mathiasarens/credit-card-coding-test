package de.arens.publics.sapient.controller;

import de.arens.publics.sapient.model.CreditCard;
import de.arens.publics.sapient.repository.CreditCardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class CreditCardController {

    static final String CREDIT_CARD_URL = "/api/creditcard";

    @Resource
    private CreditCardRepository creditCardRepository;

    @PostMapping(name = CREDIT_CARD_URL)
    public ResponseEntity<?> add(@Valid @RequestBody final CreditCard creditCard) {
        if (creditCard.getId() != null) {
            return ResponseEntity.status(303).body(String.format("Please update credit card resource via %s/%d", CREDIT_CARD_URL,creditCard.getId()));
        }
        final CreditCard savedCreditCard = creditCardRepository.save(creditCard);
        return new ResponseEntity(savedCreditCard, HttpStatus.CREATED);
    }

    @PutMapping(CREDIT_CARD_URL + "/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody final CreditCard creditCard, @PathVariable final Long id) {
        if (creditCard.getId() == null) {
            return ResponseEntity.status(303).body(String.format("Please create credit card resource via %s", CREDIT_CARD_URL));
        }
        final CreditCard savedCreditCard = creditCardRepository.save(creditCard);
        return new ResponseEntity(savedCreditCard, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = CREDIT_CARD_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<CreditCard> list() {
        Iterable<CreditCard> all = creditCardRepository.findAll();
        return all;
    }
}
