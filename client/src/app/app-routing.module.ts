import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddCreditCardComponent } from './add-credit-card/add-credit-card.component';
import { ListCreditCardsComponent } from './list-credit-cards/list-credit-cards.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

const routes: Routes = [
  { path: 'list', component: ListCreditCardsComponent },
  { path: 'add', component: AddCreditCardComponent },
  { path: '', redirectTo: '/list', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
