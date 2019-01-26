/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.arens.publics.sapient.controller;

import de.arens.publics.sapient.model.CreditCard;
import de.arens.publics.sapient.repository.CreditCardRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static de.arens.publics.sapient.controller.CreditCardController.CREDIT_CARD_URL;
import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreditCardControllerTests {

    public static final String FIELD_STATUS = "status";
    public static final String FIELD_ERROR = "error";
    public static final String FIELD_MESSAGE = "message";
    public static final String FIELD_SUB_ERRORS = "subErrors";
    public static final String FIELD_OBJECT = "object";
    public static final String FIELD_FIELD = "field";
    public static final String FIELD_REJECTED_VALUE = "rejectedValue";
    private final Logger logger = LoggerFactory.getLogger(CreditCardControllerTests.class);

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private CreditCardRepository repository;

    @Before
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void postWithCorrectDataShouldReturnEntity() throws Exception {
        final ResponseEntity<Map<String, Object>> result = post("{\"name\":\"Alice\",\"number\":\"3768195404959997840\",\"limit\":\"2000\"}");
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertTrue(result.getBody().get("id") instanceof  Integer);
        assertEquals("3768195404959997840", result.getBody().get("number"));
        assertEquals(0.0, result.getBody().get("balance"));
        assertEquals(2000.0, result.getBody().get("limit"));
    }

    @Test
    public void postWith15DigitsCreditCardNumberShouldReturnError() throws Exception {
        final ResponseEntity<Map<String, Object>> result = post("{\"name\":\"Alice\",\"number\":\"7958568664\",\"limit\":\"2000\"}");
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("BAD_REQUEST", result.getBody().get(FIELD_STATUS));
        assertEquals("apierror", result.getBody().get(FIELD_ERROR));
        assertEquals("Validation error", result.getBody().get(FIELD_MESSAGE));
        assertEquals(1, ((List) result.getBody().get(FIELD_SUB_ERRORS)).size());
        final Map<String, Object> subError0 = (Map) ((List) result.getBody().get(FIELD_SUB_ERRORS)).get(0);
        assertEquals("creditCard", subError0.get(FIELD_OBJECT));
        assertEquals("number", subError0.get(FIELD_FIELD));
        assertEquals("7958568664", subError0.get(FIELD_REJECTED_VALUE));
        assertEquals("Please provide a credit card number with 16 to 19 digits", subError0.get(FIELD_MESSAGE));
    }

    @Test
    public void postWith20DigitsCreditCardNumberShouldReturnError() throws Exception {
        final ResponseEntity<Map<String, Object>> result = post("{\"name\":\"Alice\",\"number\":\"32463278821435338971\",\"limit\":\"2000\"}");
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("BAD_REQUEST", result.getBody().get(FIELD_STATUS));
        assertEquals("apierror", result.getBody().get(FIELD_ERROR));
        assertEquals("Validation error", result.getBody().get(FIELD_MESSAGE));
        assertEquals(1, ((List) result.getBody().get(FIELD_SUB_ERRORS)).size());
        final Map<String, Object> subError0 = (Map) ((List) result.getBody().get(FIELD_SUB_ERRORS)).get(0);
        assertEquals("creditCard", subError0.get(FIELD_OBJECT));
        assertEquals("number", subError0.get(FIELD_FIELD));
        assertEquals("32463278821435338971", subError0.get(FIELD_REJECTED_VALUE));
        assertEquals("Please provide a credit card number with 16 to 19 digits", subError0.get(FIELD_MESSAGE));
    }

    @Test
    public void postWithInvalidCreditCardNumberShouldReturnError() throws Exception {
        final ResponseEntity<Map<String, Object>> result = post("{\"name\":\"Alice\",\"number\":\"3768195404959997841\",\"limit\":\"2000\"}");
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("BAD_REQUEST", result.getBody().get(FIELD_STATUS));
        assertEquals("apierror", result.getBody().get(FIELD_ERROR));
        assertEquals("Validation error", result.getBody().get(FIELD_MESSAGE));
        assertEquals(1, ((List) result.getBody().get(FIELD_SUB_ERRORS)).size());
        final Map<String, Object> subError0 = (Map) ((List) result.getBody().get(FIELD_SUB_ERRORS)).get(0);
        assertEquals("creditCard", subError0.get(FIELD_OBJECT));
        assertEquals("number", subError0.get(FIELD_FIELD));
        assertEquals("3768195404959997841", subError0.get(FIELD_REJECTED_VALUE));
        assertEquals("Invalid credit card number", subError0.get(FIELD_MESSAGE));
    }

    @Test
    public void postCreditCardNumberTwiceShouldReturnDuplicateCreditCardError() throws Exception {
        post("{\"name\":\"Steven\",\"number\":\"3768195404959997840\",\"limit\":\"2000\"}");
        final ResponseEntity<Map<String, Object>> result = post("{\"name\":\"Frank\",\"number\":\"3768195404959997840\",\"limit\":\"1000\"}");
        assertEquals("BAD_REQUEST", result.getBody().get(FIELD_STATUS));
        assertEquals("apierror", result.getBody().get(FIELD_ERROR));
        assertEquals("Validation error", result.getBody().get(FIELD_MESSAGE));
        assertEquals(1, ((List) result.getBody().get(FIELD_SUB_ERRORS)).size());
        final Map<String, Object> subError0 = (Map) ((List) result.getBody().get(FIELD_SUB_ERRORS)).get(0);
        assertEquals("creditCard", subError0.get(FIELD_OBJECT));
        assertEquals("number", subError0.get(FIELD_FIELD));
        assertEquals("3768195404959997840", subError0.get(FIELD_REJECTED_VALUE));
        assertEquals("A credit card with that number already exists", subError0.get(FIELD_MESSAGE));
    }

    @Test
    public void postCreditCardNameTwiceShouldReturnDuplicateCreditCardError() throws Exception {
        post("{\"name\":\"Steven\",\"number\":\"3768195404959997840\",\"limit\":\"2000\"}");
        final ResponseEntity<Map<String, Object>> result = post("{\"name\":\"Steven\",\"number\":\"4338849695480851429\",\"limit\":\"1000\"}");
        assertEquals("BAD_REQUEST", result.getBody().get(FIELD_STATUS));
        assertEquals("apierror", result.getBody().get(FIELD_ERROR));
        assertEquals("Validation error", result.getBody().get(FIELD_MESSAGE));
        assertEquals(1, ((List) result.getBody().get(FIELD_SUB_ERRORS)).size());
        final Map<String, Object> subError0 = (Map) ((List) result.getBody().get(FIELD_SUB_ERRORS)).get(0);
        assertEquals("creditCard", subError0.get(FIELD_OBJECT));
        assertEquals("name", subError0.get(FIELD_FIELD));
        assertEquals("Steven", subError0.get(FIELD_REJECTED_VALUE));
        assertEquals("A credit card with that name already exists", subError0.get(FIELD_MESSAGE));
    }

    @Test
    public void getCreditCardShouldReturnAllCreditCards() throws Exception {
        post("{\"name\":\"Alice\",\"number\":\"3768195404959997840\",\"limit\":\"2000\"}");
        post("{\"name\":\"Bob\",\"number\":\"4338849695480851429\",\"limit\":\"1000\"}");
        final ResponseEntity<List<CreditCard>> result = get();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals("Alice", result.getBody().get(0).getName());
        assertEquals("Bob", result.getBody().get(1).getName());
    }

    private ResponseEntity<List<CreditCard>> get() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON_UTF8);
        headers.setAccept(Collections.singletonList(APPLICATION_JSON_UTF8));
        final HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return template.exchange(CREDIT_CARD_URL, GET, httpEntity, new ParameterizedTypeReference<List<CreditCard>>() {
        });
    }

    private ResponseEntity<Map<String, Object>> post(final String content) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON_UTF8);
        headers.setAccept(Collections.singletonList(APPLICATION_JSON_UTF8));
        final HttpEntity<String> httpEntity = new HttpEntity<>(content, headers);
        return template.exchange(CREDIT_CARD_URL, POST, httpEntity, new ParameterizedTypeReference<Map<String, Object>>() {
        });
    }
}
