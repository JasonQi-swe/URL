import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HistoryResponse } from 'src/app/model/history.response.model';
import { Page } from 'src/app/model/page.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

  private backendUrl = environment.backendUrl + 'history';

  constructor(private http: HttpClient) { }

  getHistory(page: number, size: number): Observable<Page<HistoryResponse>> {
    return this.http.get<Page<HistoryResponse>>(`${this.backendUrl}?page=${page}&size=${size}`);
  }
}


