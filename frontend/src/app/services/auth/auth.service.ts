import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authUrl = 'http://localhost:8080/auth'; 
  private authState = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient) {}

  hasToken(): boolean {
    return !!localStorage.getItem('accessToken');
  }

  getAuthState(): Observable<boolean> {
    return this.authState.asObservable();
  }

  login(loginRequest: any): Observable<any> {
    return new Observable(observer => {
      this.http.post(`${this.authUrl}/login`, loginRequest).subscribe(
        (response: any) => {
          localStorage.setItem('accessToken', response.accessToken);
          localStorage.setItem('refreshToken', response.refreshToken);
          this.authState.next(true);
          observer.next(response);
          observer.complete();
        },
        (error) => {
          observer.error(error);
        }
      );
    });
  }

  refreshToken(refreshToken: string): Observable<any> {
    return this.http.post<any>(`${this.authUrl}/refresh-token`, {}, {
      headers: { 'Authorization': `Bearer ${refreshToken}` }
    });
  }

  logout(): void {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    this.authState.next(false);
  }
}
