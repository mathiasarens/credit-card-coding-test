export class CreditCard {
  public constructor(init?: Partial<CreditCard>) {
    Object.assign(this, init);
  }
  id: number;
  name: string;
  number: string;
  balance: number;
  limit: number;
}
