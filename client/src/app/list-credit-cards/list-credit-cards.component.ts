import { Component, OnInit } from '@angular/core';
import { ListCreditCardsService } from './list-credit-cards.service';
import { CreditCard } from '../creditcard.interface';

@Component({
  selector: 'app-list-credit-cards',
  templateUrl: './list-credit-cards.component.html',
  styleUrls: ['./list-credit-cards.component.scss']
})
export class ListCreditCardsComponent implements OnInit {
  columnsToDisplay: string[] = ['name', 'number', 'balance', 'limit'];
  creditcards: CreditCard[] = [];

  constructor(private listCreditCardsService: ListCreditCardsService) { }

  ngOnInit() {
    this.listCreditCardsService.listCreditCards().subscribe(
      (list: CreditCard[]) => {
        this.creditcards = list;
      },
      error => {
        console.error(error);
      });
  }

}
