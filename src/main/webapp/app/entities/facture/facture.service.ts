import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFacture } from 'app/shared/model/facture.model';

type EntityResponseType = HttpResponse<IFacture>;
type EntityArrayResponseType = HttpResponse<IFacture[]>;

@Injectable({ providedIn: 'root' })
export class FactureService {
  public resourceUrl = SERVER_API_URL + 'api/factures';

  constructor(protected http: HttpClient) {}

  create(facture: IFacture): Observable<EntityResponseType> {
    return this.http.post<IFacture>(this.resourceUrl, facture, { observe: 'response' });
  }

  update(facture: IFacture): Observable<EntityResponseType> {
    return this.http.put<IFacture>(this.resourceUrl, facture, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFacture>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFacture[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
