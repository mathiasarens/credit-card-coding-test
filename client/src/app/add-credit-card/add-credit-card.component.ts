import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AddCreditCardService } from './add-credit-card.service';
import { CreditCardImpl } from '../creditcard.impl';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-add-credit-card',
  templateUrl: './add-credit-card.component.html',
  styleUrls: ['./add-credit-card.component.scss']
})
export class AddCreditCardComponent implements OnInit {
  addCreditCardForm = new FormGroup({
    name: new FormControl('', Validators.required),
    number: new FormControl('', Validators.required),
    limit: new FormControl('', Validators.required)
  });

  constructor(private addCreditCardService: AddCreditCardService, private errorSnackBar: MatSnackBar, private router:Router) { }

  ngOnInit() {
  }

  onSubmit() {
    if (this.addCreditCardForm.invalid) {
      return;
    }
    this.addCreditCardService.addCreditCard(new CreditCardImpl(this.addCreditCardForm.value))
      .subscribe(
        response => {
          this.router.navigate(['/list']);
        },
        httpErrorResponse => {
          if (httpErrorResponse.error.error === 'apierror' && httpErrorResponse.error.subErrors.length > 0) {
            console.error('error', httpErrorResponse);
            for (let subError of httpErrorResponse.error.subErrors) {
              this.addCreditCardForm.controls[subError.field]
                .setErrors({
                  message: subError.message,
                  rejectedValue: subError.rejectedValue
                });
            }
          } else {
            this.errorSnackBar.open('Communication error.', 'Try again', { duration: 2500 });
            console.error('error', httpErrorResponse);
          }
        });
  }

  get name() {
    return this.addCreditCardForm.get('name');
  }

  get number() {
    return this.addCreditCardForm.get('number');
  }

  get limit() {
    return this.addCreditCardForm.get('limit');
  }
}
