import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClient, Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numeroCnibClient: [],
    nomClient: [],
    prenomClient: [],
    telephone1Client: [],
    telephone2Client: [],
    villeClient: [],
    emailClient: [],
  });

  constructor(protected clientService: ClientService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.updateForm(client);
    });
  }

  updateForm(client: IClient): void {
    this.editForm.patchValue({
      id: client.id,
      numeroCnibClient: client.numeroCnibClient,
      nomClient: client.nomClient,
      prenomClient: client.prenomClient,
      telephone1Client: client.telephone1Client,
      telephone2Client: client.telephone2Client,
      villeClient: client.villeClient,
      emailClient: client.emailClient,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const client = this.createFromForm();
    if (client.id !== undefined) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  private createFromForm(): IClient {
    return {
      ...new Client(),
      id: this.editForm.get(['id'])!.value,
      numeroCnibClient: this.editForm.get(['numeroCnibClient'])!.value,
      nomClient: this.editForm.get(['nomClient'])!.value,
      prenomClient: this.editForm.get(['prenomClient'])!.value,
      telephone1Client: this.editForm.get(['telephone1Client'])!.value,
      telephone2Client: this.editForm.get(['telephone2Client'])!.value,
      villeClient: this.editForm.get(['villeClient'])!.value,
      emailClient: this.editForm.get(['emailClient'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
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
}
