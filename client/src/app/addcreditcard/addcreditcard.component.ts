import { Component, OnInit, Injectable } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import {AddCreditCardService} from './addcreditcard.service';
import { CreditCard } from '../creditcard';

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
  constructor(private addCreditCardService: AddCreditCardService) {}

  ngOnInit() {
  }

  onSubmit() {
    this.addCreditCardService.addCreditCard(new CreditCard(this.addCreditCardForm.value))
      .subscribe(resp => {
          console.log(resp.body);
      });
  }

}
