import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionStockSharedModule } from 'app/shared/shared.module';
import { ClientComponent } from './client.component';
import { ClientDetailComponent } from './client-detail.component';
import { ClientUpdateComponent } from './client-update.component';
import { ClientDeleteDialogComponent } from './client-delete-dialog.component';
import { clientRoute } from './client.route';

@NgModule({
  imports: [GestionStockSharedModule, RouterModule.forChild(clientRoute)],
  declarations: [ClientComponent, ClientDetailComponent, ClientUpdateComponent, ClientDeleteDialogComponent],
  entryComponents: [ClientDeleteDialogComponent],
})
export class GestionStockClientModule {}
