import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { CreditCardImpl } from '../creditcard.impl';

@Injectable()
export class AddCreditCardService {
    url = '/api/creditcard';

    constructor(private http: HttpClient) { }

    addCreditCard(creditCard: CreditCardImpl): Observable<HttpResponse<Object>> {
        return this.http.post(this.url, creditCard, {
            headers: new HttpHeaders({
                'Content-Type':  'application/json'
            }), observe: 'response'});
    }
}
