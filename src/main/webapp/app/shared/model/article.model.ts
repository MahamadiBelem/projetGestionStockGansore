import { Moment } from 'moment';
import { IFacture } from 'app/shared/model/facture.model';
import { ICategorie } from 'app/shared/model/categorie.model';

export interface IArticle {
  id?: number;
  codeArticle?: string;
  libelleArticle?: string;
  paArticle?: number;
  pvArticle?: number;
  stockInitialArticle?: number;
  dateEnregistrementArticle?: Moment;
  imageArticleContentType?: string;
  imageArticle?: any;
  factures?: IFacture[];
  categorie?: ICategorie;
}

export class Article implements IArticle {
  constructor(
    public id?: number,
    public codeArticle?: string,
    public libelleArticle?: string,
    public paArticle?: number,
    public pvArticle?: number,
    public stockInitialArticle?: number,
    public dateEnregistrementArticle?: Moment,
    public imageArticleContentType?: string,
    public imageArticle?: any,
    public factures?: IFacture[],
    public categorie?: ICategorie
  ) {}
}
