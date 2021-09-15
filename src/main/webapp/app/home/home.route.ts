import { Route, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ArticleDetailComponent } from 'app/entities/article/article-detail.component';
import { ArticleKnownComponent } from 'app/entities/article/article-known/article-known.component';
import { ArticleUpdateComponent } from 'app/entities/article/article-update.component';
import { ArticleComponent } from 'app/entities/article/article.component';
import { ArticleResolve } from 'app/entities/article/article.route';
import { Authority } from 'app/shared/constants/authority.constants';

import { HomeComponent } from './home.component';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    authorities: [],
    pageTitle: 'GANSONREStock!',
  },
};

//** THESE COME FROM ARTICLES ROUTE.TS */
export const articleRoute: Routes = [
  {
    path: '',
    component: ArticleComponent,
    data: {
      authorities: [],
      defaultSort: 'id,asc',
      pageTitle: 'Articles',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArticleDetailComponent,
    resolve: {
      article: ArticleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Articles',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArticleUpdateComponent,
    resolve: {
      article: ArticleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Articles',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArticleUpdateComponent,
    resolve: {
      article: ArticleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Articles',
    },
    canActivate: [UserRouteAccessService],
  },


  {
    path: 'known',
    component: ArticleKnownComponent,
    resolve: {
      article: ArticleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Articles',
    },
    canActivate: [UserRouteAccessService],
  }
];
