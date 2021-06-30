import { ICommande } from 'app/shared/model/commande.model';

export interface IClient {
  id?: number;
  numeroCnibClient?: string;
  nomClient?: string;
  prenomClient?: string;
  telephone1Client?: string;
  telephone2Client?: string;
  villeClient?: string;
  emailClient?: string;
  commandes?: ICommande[];
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public numeroCnibClient?: string,
    public nomClient?: string,
    public prenomClient?: string,
    public telephone1Client?: string,
    public telephone2Client?: string,
    public villeClient?: string,
    public emailClient?: string,
    public commandes?: ICommande[]
  ) {}
}
