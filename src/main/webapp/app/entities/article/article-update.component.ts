import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IArticle, Article } from 'app/shared/model/article.model';
import { ArticleService } from './article.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from 'app/entities/categorie/categorie.service';

@Component({
  selector: 'jhi-article-update',
  templateUrl: './article-update.component.html',
})
export class ArticleUpdateComponent implements OnInit {
  isSaving = false;
  categories: ICategorie[] = [];
  dateEnregistrementArticleDp: any;

  editForm = this.fb.group({
    id: [],
    codeArticle: [null, [Validators.required]],
    libelleArticle: [],
    paArticle: [],
    pvArticle: [],
    stockInitialArticle: [],
    dateEnregistrementArticle: [],
    imageArticle: [],
    imageArticleContentType: [],
    categorie: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected articleService: ArticleService,
    protected categorieService: CategorieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ article }) => {
      this.updateForm(article);

      this.categorieService.query().subscribe((res: HttpResponse<ICategorie[]>) => (this.categories = res.body || []));
    });
  }

  updateForm(article: IArticle): void {
    this.editForm.patchValue({
      id: article.id,
      codeArticle: article.codeArticle,
      libelleArticle: article.libelleArticle,
      paArticle: article.paArticle,
      pvArticle: article.pvArticle,
      stockInitialArticle: article.stockInitialArticle,
      dateEnregistrementArticle: article.dateEnregistrementArticle,
      imageArticle: article.imageArticle,
      imageArticleContentType: article.imageArticleContentType,
      categorie: article.categorie,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('gestionStockApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const article = this.createFromForm();
    if (article.id !== undefined) {
      this.subscribeToSaveResponse(this.articleService.update(article));
    } else {
      this.subscribeToSaveResponse(this.articleService.create(article));
    }
  }

  private createFromForm(): IArticle {
    return {
      ...new Article(),
      id: this.editForm.get(['id'])!.value,
      codeArticle: this.editForm.get(['codeArticle'])!.value,
      libelleArticle: this.editForm.get(['libelleArticle'])!.value,
      paArticle: this.editForm.get(['paArticle'])!.value,
      pvArticle: this.editForm.get(['pvArticle'])!.value,
      stockInitialArticle: this.editForm.get(['stockInitialArticle'])!.value,
      dateEnregistrementArticle: this.editForm.get(['dateEnregistrementArticle'])!.value,
      imageArticleContentType: this.editForm.get(['imageArticleContentType'])!.value,
      imageArticle: this.editForm.get(['imageArticle'])!.value,
      categorie: this.editForm.get(['categorie'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticle>>): void {
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

  trackById(index: number, item: ICategorie): any {
    return item.id;
  }
}
