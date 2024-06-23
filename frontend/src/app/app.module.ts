import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { NavComponent } from './components/nav/nav.component';
import { RouterModule, Routes } from '@angular/router';
import { AnalyseComponent } from './components/analyse/analyse.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HistoryComponent } from './components/history/history.component';

const appRoutes: Routes = [
  { path: '', component: AnalyseComponent },
  { path: 'analyse', component: AnalyseComponent },
  { path: 'history', component: HistoryComponent },
]

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavComponent,
    AnalyseComponent,
    HistoryComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
