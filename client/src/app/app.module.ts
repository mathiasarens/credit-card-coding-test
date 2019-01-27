import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule, MatButtonModule, MatSnackBarModule } from '@angular/material';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTableModule } from '@angular/material/table';

import { AppComponent } from './app.component';
import { AddCreditCardComponent } from './add-credit-card/add-credit-card.component';
import { ListCreditCardsComponent } from './list-credit-cards/list-credit-cards.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

import { ListCreditCardsService } from './list-credit-cards/list-credit-cards.service';
import { AddCreditCardService } from './add-credit-card/add-credit-card.service';

@NgModule({
  declarations: [
    AppComponent,
    AddCreditCardComponent,
    ListCreditCardsComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FlexLayoutModule,
    AppRoutingModule,
    BrowserAnimationsModule, // new modules added here
    MatToolbarModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule,
    ReactiveFormsModule,
    MatTableModule
  ],
  providers: [
    AddCreditCardService,
    ListCreditCardsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
