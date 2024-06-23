import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { AnalyseService } from 'src/app/services/analyse/analyse.service';

@Component({
  selector: 'app-analyse',
  templateUrl: './analyse.component.html',
  styleUrls: ['./analyse.component.css']
})
export class AnalyseComponent implements OnInit {

  url: string = 'http://example.com';
  allLinks: string[] = [];
  paginatedLinks: string[] = [];
  errorMessage: string = '';
  currentPage: number = 0;
  pageSize: number = 10;
  totalPages: number = 0;
  maxLength: number = 2048;

  constructor(private analyseService: AnalyseService, public sanitizer: DomSanitizer) {}

  ngOnInit(): void {}

  fetchLinks() {
    if (this.url.length > this.maxLength) {
      this.errorMessage = `URL cannot exceed ${this.maxLength} characters.`;
      return;
    }

    this.analyseService.fetchLinks(this.url).subscribe({
      next: (response) => {
        this.allLinks = response.links;
        this.totalPages = Math.ceil(this.allLinks.length / this.pageSize);
        this.updatePaginatedLinks();
        this.errorMessage = '';
      },
      error: (error: Error) => {
        //console.log("error.status in component:" + error.status);
        console.error("error in the component layer");
        this.errorMessage = error.message;
      }
    });
  }

  updatePaginatedLinks() {
    const start = this.currentPage * this.pageSize;
    const end = start + this.pageSize;
    this.paginatedLinks = this.allLinks.slice(start, end);
  }

  goToPage(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.updatePaginatedLinks();
    }
  }
}