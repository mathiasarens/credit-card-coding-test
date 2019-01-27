import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { CreditCard } from '../creditcard.interface';

@Injectable()
export class ListCreditCardsService {
    url = '/api/creditcard';

    constructor(private http: HttpClient) { }

    listCreditCards(): Observable<CreditCard[]> {
        return this.http.get<CreditCard[]>(this.url);
    }
}
