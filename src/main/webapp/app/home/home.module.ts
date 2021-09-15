import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionStockSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { ArticleDeleteDialogComponent } from 'app/entities/article/article-delete-dialog.component';
import { ArticleDetailComponent } from 'app/entities/article/article-detail.component';
import { ArticleComponent } from 'app/entities/article/article.component';
import { ArticleUpdateComponent } from 'app/entities/article/article-update.component';

@NgModule({
  imports: [GestionStockSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent,
    // THESE COME FROM ARTICLE
    ArticleComponent, ArticleDetailComponent, ArticleUpdateComponent, ArticleDeleteDialogComponent],
    entryComponents: [ArticleDeleteDialogComponent],
})
export class GestionStockHomeModule {}
