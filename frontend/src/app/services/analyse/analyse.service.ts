import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AnalyseResponse } from 'src/app/model/analyse.response.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AnalyseService {

  private backendUrl = environment.backendUrl + 'analyse';

  constructor(private http: HttpClient) {}

  fetchLinks(url: string): Observable<AnalyseResponse> {
    return this.http.post<AnalyseResponse>(this.backendUrl, { url }).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    console.log("error.status:" + error.status);
    let errorMessage = 'Failed to fetch links from the backend';
    if (error.error && error.error.message) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error ${error.status}: ${error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }
}


