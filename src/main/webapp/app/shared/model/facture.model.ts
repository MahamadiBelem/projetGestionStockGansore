import { ICommande } from 'app/shared/model/commande.model';
import { IArticle } from 'app/shared/model/article.model';

export interface IFacture {
  id?: number;
  numeroFacture?: string;
  libelleFacture?: any;
  prescipteurFacture?: string;
  commande?: ICommande;
  article?: IArticle;
}

export class Facture implements IFacture {
  constructor(
    public id?: number,
    public numeroFacture?: string,
    public libelleFacture?: any,
    public prescipteurFacture?: string,
    public commande?: ICommande,
    public article?: IArticle
  ) {}
}
