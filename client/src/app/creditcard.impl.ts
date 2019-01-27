import { CreditCard } from './creditcard.interface';

export class CreditCardImpl implements CreditCard {
  public constructor(init?: Partial<CreditCard>) {
    Object.assign(this, init);
  }
  id: number;
  name: string;
  number: string;
  balance: number;
  limit: number;
}
