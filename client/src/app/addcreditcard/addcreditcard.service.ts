import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { CreditCard } from '../creditcard';

@Injectable()
export class AddCreditCardService {
    url = '/api/creditcard';

    constructor(private http: HttpClient) { }

    addCreditCard(creditCard: CreditCard): Observable<HttpResponse<Object>> {
        return this.http.post(this.url, creditCard, {
            headers: new HttpHeaders({
                'Content-Type':  'application/json'
            }), observe: 'response'}).pipe(catchError(this.handleError));
    }

    private handleError(error: HttpErrorResponse) {
        if (error.error instanceof ErrorEvent) {
            // A client-side or network error occurred. Handle it accordingly.
            console.error('An error occurred:', error.error.message);
          } else {
            // The backend returned an unsuccessful response code.
            // The response body may contain clues as to what went wrong,
            console.error(
              `Backend returned code ${error.status}, ` +
              `body was: ${error.error}`);
          }
          // return an observable with a user-facing error message
          return throwError('Something bad happened; please try again later.');
    }
}
