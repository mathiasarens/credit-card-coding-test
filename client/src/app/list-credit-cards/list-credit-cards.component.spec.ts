import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListCreditCardsComponent } from './list-credit-cards.component';

describe('ListcreditcardsComponent', () => {
  let component: ListCreditCardsComponent;
  let fixture: ComponentFixture<ListCreditCardsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListCreditCardsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListCreditCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
