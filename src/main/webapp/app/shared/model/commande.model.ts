import { Moment } from 'moment';
import { IFacture } from 'app/shared/model/facture.model';
import { IClient } from 'app/shared/model/client.model';

export interface ICommande {
  id?: number;
  codeCommande?: string;
  dateCommande?: Moment;
  libelleCommande?: string;
  quantiteCommande?: number;
  statusCommande?: boolean;
  montantVerseCommande?: number;
  dateLivraison?: Moment;
  factures?: IFacture[];
  client?: IClient;
}

export class Commande implements ICommande {
  constructor(
    public id?: number,
    public codeCommande?: string,
    public dateCommande?: Moment,
    public libelleCommande?: string,
    public quantiteCommande?: number,
    public statusCommande?: boolean,
    public montantVerseCommande?: number,
    public dateLivraison?: Moment,
    public factures?: IFacture[],
    public client?: IClient
  ) {
    this.statusCommande = this.statusCommande || false;
  }
}
