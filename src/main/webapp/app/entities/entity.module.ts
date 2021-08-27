import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'article',
        loadChildren: () => import('./article/article.module').then(m => m.GestionStockArticleModule),
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.GestionStockClientModule),
      },
      {
        path: 'commande',
        loadChildren: () => import('./commande/commande.module').then(m => m.GestionStockCommandeModule),
      },
      {
        path: 'categorie',
        loadChildren: () => import('./categorie/categorie.module').then(m => m.GestionStockCategorieModule),
      },
      {
        path: 'facture',
        loadChildren: () => import('./facture/facture.module').then(m => m.GestionStockFactureModule),
      },
      {
        path: 'ligne-commande',
        loadChildren: () => import('./ligne-commande/ligne-commande.module').then(m => m.GestionStockLigneCommandeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GestionStockEntityModule {}
