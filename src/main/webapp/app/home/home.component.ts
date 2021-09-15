import { Component, OnInit, OnDestroy } from '@angular/core';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { IArticle } from 'app/shared/model/article.model';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { ArticleService } from 'app/entities/article/article.service';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription, combineLatest } from 'rxjs';
import { ArticleDeleteDialogComponent } from 'app/entities/article/article-delete-dialog.component';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;
  isNavbarCollapsed!: boolean;


  /******THESE ARE THE PROPERTY COME THROUGHT ARTICLE */
  articles?: IArticle[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  filter = '';
  orderProp = 'name';
  reverse = false;

  constructor(private accountService: AccountService,
     private loginModalService: LoginModalService,
     /** THESE COME FROM ARTICLE CONSTRUCTOR */
     protected articleService: ArticleService,
     protected activatedRoute: ActivatedRoute,
     protected dataUtils: JhiDataUtils,
     protected router: Router,
     protected eventManager: JhiEventManager,
     protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.handleNavigation();
    this.registerChangeInArticles();
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }


  //******  THESE ARE DIFFERENT FUNCTIONS FROM ARTICLES TO MAKE A KIND OF INJECTION*/
  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.articleService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IArticle[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }


  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IArticle): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInArticles(): void {
    this.eventSubscriber = this.eventManager.subscribe('articleListModification', () => this.loadPage());
  }

  delete(article: IArticle): void {
    const modalRef = this.modalService.open(ArticleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.article = article;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IArticle[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/article'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.articles = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }


}
