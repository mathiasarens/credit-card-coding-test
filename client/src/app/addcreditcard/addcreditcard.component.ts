import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

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
    constructor() {}

  ngOnInit() {
  }

}