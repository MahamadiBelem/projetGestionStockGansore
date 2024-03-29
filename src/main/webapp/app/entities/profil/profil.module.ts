import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionStockSharedModule } from 'app/shared/shared.module';
import { ProfilComponent } from './profil.component';
import { ProfilDetailComponent } from './profil-detail.component';
import { ProfilUpdateComponent } from './profil-update.component';
import { ProfilDeleteDialogComponent } from './profil-delete-dialog.component';
import { profilRoute } from './profil.route';

@NgModule({
  imports: [GestionStockSharedModule, RouterModule.forChild(profilRoute)],
  declarations: [ProfilComponent, ProfilDetailComponent, ProfilUpdateComponent, ProfilDeleteDialogComponent],
  entryComponents: [ProfilDeleteDialogComponent],
})
export class GestionStockProfilModule {}
