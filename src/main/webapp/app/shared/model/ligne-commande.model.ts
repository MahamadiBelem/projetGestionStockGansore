export interface ILigneCommande {
  id?: number;
  description?: string;
  commandeId?: number;
  articleId?: number;
}

export class LigneCommande implements ILigneCommande {
  constructor(public id?: number, public description?: string, public commandeId?: number, public articleId?: number) {}
}
