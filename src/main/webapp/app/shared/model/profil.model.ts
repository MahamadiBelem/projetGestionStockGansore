export interface IProfil {
  id?: number;
  nomProfil?: string;
  description?: string;
}

export class Profil implements IProfil {
  constructor(public id?: number, public nomProfil?: string, public description?: string) {}
}
