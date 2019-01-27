import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { CreditCard } from '../creditcard.interface';
import { environment } from '../../environments/environment';

@Injectable()
export class ListCreditCardsService {
    url = '/api/creditcard';

    constructor(private http: HttpClient) { }

    listCreditCards(): Observable<CreditCard[]> {
        return this.http.get<CreditCard[]>(environment.apiUrl + this.url);
    }
}
