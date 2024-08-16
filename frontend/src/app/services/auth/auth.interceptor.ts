import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private isRefreshing = false;

  constructor(private authService: AuthService, private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const accessToken = localStorage.getItem('accessToken');

    let authReq = req;

    if (accessToken && !req.url.endsWith('/refresh-token')) {
      authReq = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${accessToken}`)
      });
    }

    return next.handle(authReq).pipe(
      catchError((error: HttpErrorResponse) => {
        if ((error.status === 401 || error.status === 403) && !this.isRefreshing) {
          this.isRefreshing = true;
          const refreshToken = localStorage.getItem('refreshToken');
          if (refreshToken) {
            return this.authService.refreshToken(refreshToken).pipe(
              switchMap((tokens: any) => {
                this.isRefreshing = false;
                localStorage.setItem('accessToken', tokens.accessToken);
                localStorage.setItem('refreshToken', tokens.refreshToken);

                authReq = req.clone({
                  headers: req.headers.set('Authorization', `Bearer ${tokens.accessToken}`)
                });

                return next.handle(authReq);
              }),
              catchError((refreshError) => {
                this.isRefreshing = false;
                this.router.navigate(['/']);
                return throwError(refreshError);
              })
            );
          } else {
            this.isRefreshing = false;
            this.router.navigate(['/']);
          }
        }

        return throwError(error);
      })
    );
  }
}
