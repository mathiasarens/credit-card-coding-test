import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AddCreditCardService } from './addcreditcard.service';
import { CreditCard } from '../creditcard';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-addcreditcard',
  templateUrl: './addcreditcard.component.html',
  styleUrls: ['./addcreditcard.component.scss']
})
export class AddCreditCardComponent implements OnInit {
  addCreditCardForm = new FormGroup({
    name: new FormControl(''),
    number: new FormControl(''),
    limit: new FormControl('')
  });
  nameError = '';
  constructor(private addCreditCardService: AddCreditCardService, private errorSnackBar: MatSnackBar) { }

  ngOnInit() {
  }

  onSubmit() {
    this.addCreditCardService.addCreditCard(new CreditCard(this.addCreditCardForm.value))
      .subscribe(
        response => {
          console.log('response', response.body);
        },
        httpErrorResponse => {
          if (httpErrorResponse.error.error === 'apierror' && httpErrorResponse.error.subErrors.length > 0) {
            console.error('error', httpErrorResponse);
            this.addCreditCardForm.controls['name'].setErrors({ 'incorrect': true });
          } else {
            this.errorSnackBar.open('Communication error.', 'Try again', { duration: 2500 });
            console.error('error', httpErrorResponse);
          }
        });
  }
}
