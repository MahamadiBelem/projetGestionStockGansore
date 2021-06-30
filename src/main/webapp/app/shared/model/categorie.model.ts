import { IArticle } from 'app/shared/model/article.model';

export interface ICategorie {
  id?: number;
  codeCategorie?: string;
  libelleCategorie?: string;
  articles?: IArticle[];
}

export class Categorie implements ICategorie {
  constructor(public id?: number, public codeCategorie?: string, public libelleCategorie?: string, public articles?: IArticle[]) {}
}
