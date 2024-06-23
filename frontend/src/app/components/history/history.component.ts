import { Component, OnInit } from '@angular/core';
import { HistoryResponse } from 'src/app/model/history.response.model';
import { HistoryService } from 'src/app/services/history/history.service';


@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  histories: HistoryResponse[] = [];
  currentPage = 0;
  pageSize = 10;
  totalPages = 0;
  errorMessage = '';

  constructor(private historyService: HistoryService) { }

  ngOnInit(): void {
    this.loadHistory(this.currentPage);
  }

  loadHistory(page: number): void {
    this.historyService.getHistory(page, this.pageSize).subscribe({
      next: (response) => {
        this.histories = response.content;
        this.totalPages = response.totalPages;
        this.errorMessage = '';
      },
      error: (error) => {
        this.errorMessage = 'Failed to fetch history';
        console.error(error);
      }
    });
  }

  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadHistory(page);
    }
  }
}
