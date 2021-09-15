import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommande, Commande } from 'app/shared/model/commande.model';
import { CommandeService } from './commande.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';

@Component({
  selector: 'jhi-commande-update',
  templateUrl: './commande-update.component.html',
})
export class CommandeUpdateComponent implements OnInit {
  isSaving = false;
  clients: IClient[] = [];
  dateCommandeDp: any;
  dateLivraisonDp: any;

  editForm = this.fb.group({
    id: [],
    codeCommande: [],
    dateCommande: [],
    libelleCommande: [],
    quantiteCommande: [],
    statusCommande: [],
    montantVerseCommande: [],
    dateLivraison: [],
    client: [],
  });

  constructor(
    protected commandeService: CommandeService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commande }) => {
      this.updateForm(commande);

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));
    });
  }

  updateForm(commande: ICommande): void {
    this.editForm.patchValue({
      id: commande.id,
      codeCommande: commande.codeCommande,
      dateCommande: commande.dateCommande,
      libelleCommande: commande.libelleCommande,
      quantiteCommande: commande.quantiteCommande,
      statusCommande: commande.statusCommande,
      montantVerseCommande: commande.montantVerseCommande,
      dateLivraison: commande.dateLivraison,
      client: commande.client,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commande = this.createFromForm();
    if (commande.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeService.update(commande));
    } else {
      this.subscribeToSaveResponse(this.commandeService.create(commande));
    }
  }

  private createFromForm(): ICommande {
    return {
      ...new Commande(),
      id: this.editForm.get(['id'])!.value,
      codeCommande: this.editForm.get(['codeCommande'])!.value,
      dateCommande: this.editForm.get(['dateCommande'])!.value,
      libelleCommande: this.editForm.get(['libelleCommande'])!.value,
      quantiteCommande: this.editForm.get(['quantiteCommande'])!.value,
      statusCommande: this.editForm.get(['statusCommande'])!.value,
      montantVerseCommande: this.editForm.get(['montantVerseCommande'])!.value,
      dateLivraison: this.editForm.get(['dateLivraison'])!.value,
      client: this.editForm.get(['client'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommande>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IClient): any {
    return item.id;
  }
}
